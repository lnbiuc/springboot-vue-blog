package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.*;
import top.beyondhorizon.mapper.*;
import top.beyondhorizon.model.dto.DataYearMonth;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.*;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.model.vo.AuthorVo;
import top.beyondhorizon.model.vo.UserVo;
import top.beyondhorizon.service.ArticleService;
import top.beyondhorizon.service.ThreadService;
import top.beyondhorizon.service.TokenService;
import top.beyondhorizon.service.UserService;
import top.beyondhorizon.utils.UuidBuilder;

import java.util.*;

/**
 * ClassName: ArticleServiceImpl
 * date: 2022/5/30 18:20
 *
 * @author ayanamirei
 */

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {
    
    private final ArticleMapper articleMapper;
    
    private final TagMapper tagMapper;
    
    private final UserService userService;
    
    private final ContentMapper contentMapper;
    
    private final ThreadService threadService;
    
    private final TagNameMapper tagNameMapper;
    
    private final TokenService tokenService;
    
    private final FilingNameMapper filingNameMapper;
    
    private final FilingMapper filingMapper;
    
    
    public ArticleServiceImpl(ArticleMapper articleMapper, TagMapper tagMapper, UserServiceImpl userService,
                              ContentMapper contentMapper, ThreadService threadService, TagNameMapper tagNameMapper,
                              TokenService tokenService, FilingNameMapper filingNameMapper, FilingMapper filingMapper) {
        this.articleMapper = articleMapper;
        this.tagMapper = tagMapper;
        this.userService = userService;
        this.contentMapper = contentMapper;
        this.threadService = threadService;
        this.tagNameMapper = tagNameMapper;
        this.tokenService = tokenService;
        this.filingNameMapper = filingNameMapper;
        this.filingMapper = filingMapper;
    }
    
    @Override
    public PageInfoParams getLastUpdateArticle(PageParams pageParams, boolean isTags, boolean isAuthor,
                                               boolean needContent) {
        log.info("获取最新文章列表");
        Page<Article> page = new Page<>(pageParams.getPageNumber(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (needContent) {
            queryWrapper.orderByDesc(Article::getSetTop, Article::getUpdateTime);
        } else {
            queryWrapper.orderByDesc(Article::getSetTop, Article::getUpdateTime)
                    .select(Article.class, info -> ! info.getColumn().equals("content"));
        }
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        List<ArticleVo> articleVos = copyList(articles, isTags, isAuthor, needContent);
        PageInfoParams params = new PageInfoParams();
        params.setArticleVos(articleVos);
        params.setPageNumber(pageParams.getPageNumber());
        params.setPageSize(pageParams.getPageSize());
        params.setTotal((int) page.getTotal());
        params.setCurrentPage((int) page.getCurrent());
        return params;
        
    }
    
    public List<ArticleVo> copyList(List<Article> articleList, boolean isTags, boolean isAuthor, boolean needContent) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList) {
            articleVoList.add(copy(article, isTags, isAuthor, needContent));
        }
        return articleVoList;
    }
    
    public ArticleVo copy(Article article, boolean isTags, boolean isAuthor, boolean needContent) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isTags) {
            List<TagName> tagNameList = tagMapper.getTagNameByArticleId(article.getArticleId());
            articleVo.setTagNames(tagNameList);
        }
        if (isAuthor) {
            AuthorVo authorVo = userService.getAuthorByUserId(article.getAuthorId());
            articleVo.setAuthorVo(authorVo);
        }
        //设置内容
        if (needContent) {
            LambdaQueryWrapper<Content> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Content::getArticleId, article.getArticleId());
            articleVo.setContent(contentMapper.selectOne(queryWrapper).getContent());
        }
        //查处归档标签
        LambdaQueryWrapper<Filing> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Filing::getArticleId, article.getArticleId()).last("limit 1");
        Filing filing = filingMapper.selectOne(queryWrapper1);
        if (filing == null) {
            //如果没有设置归档直接返回
            return articleVo;
        }
        //查处归档的文件夹名称
        LambdaQueryWrapper<FilingName> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(FilingName::getId, filing.getFilingId());
        FilingName filingName = filingNameMapper.selectOne(queryWrapper2);
        articleVo.setFilingName(filingName);
        return articleVo;
    }
    
    @Override
    public ArticleVo getArticleById(String articleId) {
        log.warn("查看一篇文章，{}", articleId);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, articleId).last("limit 1");
        Article article = articleMapper.selectOne(queryWrapper);
        if (article == null) {
            return null;
        }
        ArticleVo articleVo = copy(article, true, true, true);
        log.warn("增加阅读数start");
        //线程池
        Integer oldViewCount = article.getVisitsCount();
        Article updateArticle = new Article();
        updateArticle.setVisitsCount(oldViewCount + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getArticleId, articleId);
        //乐观锁
        updateWrapper.eq(Article::getVisitsCount, oldViewCount);
        threadService.updateViewCount(updateWrapper, updateArticle);
        log.warn("请求文章成功");
        return articleVo;
    }
    
    @Override
    public RetMsg publishArticle(PublishParams publishParams, String token) {
        //验证码token查看用户是否登陆
        if (token == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        //获取登陆用户
        UserVo userByToken = tokenService.getUserByToken(token);
        if (userByToken == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        if (publishParams == null) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        Article article = new Article();
        String articleId;
        String reqId = publishParams.getArticleId();
        if (! "".equals(reqId)) {
            return RetMsg.fail(ErrorCode.ARTICLE_HAS_BE_PUBLISH.getCode(),
                    ErrorCode.ARTICLE_HAS_BE_PUBLISH.getMsg());
        }
        articleId = UuidBuilder.getUuid();
        article.setArticleId(articleId);
        article.setAuthorId(userByToken.getId());
        article.setTitle(publishParams.getTitle());
        article.setIntroduction(publishParams.getIntroduction());
        article.setReleaseTime(new Date());
        article.setUpdateTime(new Date());
        article.setBgImg(publishParams.getBgImg());
        //获取文章内容
        String articleContent = publishParams.getContent();
        Content content = new Content();
        content.setArticleId(articleId);
        content.setContent(articleContent);
        //将文章正文保存到内容表
        int i1 = contentMapper.insert(content);
        //获取文章分类信息
        addFiling(publishParams.getFilingName(), articleId);
        //获取文章标签
        List<String> tagNames = publishParams.getTag();
        //如果有标签则设置标签
        addTags(tagNames, articleId);
        int i2 = articleMapper.insert(article);
        if (i1 > 0 && i2 > 0) {
            return RetMsg.success(article.getArticleId());
        }
        return RetMsg.error("发表失败");
    }
    
    private void addFiling(String filingName, String articleId) {
        if (filingName == null || filingName.trim().equals("")) {
            return;
        }
        LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FilingName::getFilingName, filingName);
        FilingName exist = filingNameMapper.selectOne(queryWrapper);
        if (exist == null) {
            filingNameMapper.insert(new FilingName(null, filingName));
            LambdaQueryWrapper<FilingName> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(FilingName::getFilingName, filingName).last("limit 1");
            exist = filingNameMapper.selectOne(queryWrapper1);
        }
        filingMapper.insert(new Filing(articleId, exist.getId()));
    }
    
    //给文章添加tag
    private void addTags(List<String> tagNames, String articleId) {
        if (tagNames.size() == 0) {
            return;
        }
        for (String tagName : tagNames) {
            LambdaQueryWrapper<TagName> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TagName::getTagName, tagName).last("limit 1");
            //根据接收到的tagName查询数据库
            TagName exist = tagNameMapper.selectOne(queryWrapper);
            //如果这个tag不存在则创建一个新tag
            if (exist == null) {
                tagNameMapper.insert(new TagName(null, tagName));
                LambdaQueryWrapper<TagName> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(TagName::getTagName, tagName).last("limit 1");
                exist = tagNameMapper.selectOne(queryWrapper);
            }
            //如果这个他给存在，将信息设置到关联表中
            tagMapper.insert(new Tag(articleId, exist.getTagId()));
        }
    }
    
    @Override
    public RetMsg edit(EditParams editParams, String token) {
        //使用token进行身份验证
        UserVo userByToken = tokenService.getUserByToken(token);
        if (userByToken == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        String requestUserId = userByToken.getId();
        //查询需要修改的文章是否存在
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, editParams.getArticleId()).
                last("limit 1");
        Article exist = articleMapper.selectOne(queryWrapper);
        if (exist == null) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        //查询当前文章是不是该作者的文章
        if (! exist.getAuthorId().equals(requestUserId)) {
            return RetMsg.fail(ErrorCode.NO_AUTHOR.getCode(),
                    ErrorCode.NO_AUTHOR.getMsg());
        }
        //执行更新
        Article article = new Article();
        if (editParams.getTitle() != null) {
            article.setTitle(editParams.getTitle());
        }
        if (editParams.getIntroduction() != null) {
            article.setIntroduction(editParams.getIntroduction());
        }
        if (editParams.getBgImg() != null) {
            article.setBgImg(editParams.getBgImg());
        }
        if (editParams.getTag().size() > 0) {
            //如果对tag有修改就删除原有的tag重新添加
            LambdaQueryWrapper<Tag> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Tag::getArticleId, editParams.getArticleId());
            tagMapper.delete(queryWrapper1);
            addTags(editParams.getTag(), editParams.getArticleId());
        }
        if (editParams.getContent() != null) {
            Content content = new Content();
            content.setContent(editParams.getContent());
            LambdaUpdateWrapper<Content> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Content::getArticleId, editParams.getArticleId());
            contentMapper.update(content, updateWrapper);
        }
        if (editParams.getFilingName() != null) {
            //设置归档，如果没有创建一个归档文件夹，如果有则进行设置
            LambdaQueryWrapper<FilingName> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(FilingName::getFilingName, editParams.getFilingName()).last("limit 1");
            FilingName existFilingName = filingNameMapper.selectOne(queryWrapper1);
            if (existFilingName != null) {
                LambdaQueryWrapper<Filing> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(Filing::getArticleId, editParams.getArticleId());
                filingMapper.delete(queryWrapper2);
                filingMapper.insert(new Filing(editParams.getArticleId(), existFilingName.getId()));
            } else {
                filingNameMapper.insert(new FilingName(null, editParams.getFilingName()));
                LambdaQueryWrapper<FilingName> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(FilingName::getFilingName, editParams.getFilingName());
                Integer id = filingNameMapper.selectOne(queryWrapper2).getId();
                filingMapper.insert(new Filing(editParams.getArticleId(), id));
            }
        }
        //更新文章更新时间
        article.setUpdateTime(new Date());
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getArticleId, editParams.getArticleId());
        int i = articleMapper.update(article, updateWrapper);
        if (i < 1) {
            return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                    ErrorCode.UPDATE_FILE.getMsg());
        }
        return RetMsg.success(null);
    }
    
    @Override
    public RetMsg delete(String articleId, String token) {
        if (articleId == null) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        if (token == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        UserVo userByToken = tokenService.getUserByToken(token);
        if (userByToken == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        String requestUserId = userByToken.getId();
        
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, articleId).last("limit 1");
        Article article = articleMapper.selectOne(queryWrapper);
        if (article == null) {
            return RetMsg.fail(ErrorCode.ARTICLE_NOT_EXISTS.getCode(),
                    ErrorCode.ARTICLE_NOT_EXISTS.getMsg());
        }
        if (! article.getAuthorId().equals(requestUserId)) {
            return RetMsg.fail(ErrorCode.NO_AUTHOR.getCode(),
                    ErrorCode.NO_AUTHOR.getMsg());
        }
        articleMapper.deleteById(articleId);
        LambdaQueryWrapper<Content> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Content::getArticleId, articleId);
        contentMapper.delete(queryWrapper1);
        LambdaQueryWrapper<Filing> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Filing::getArticleId, articleId);
        filingMapper.delete(queryWrapper2);
        LambdaQueryWrapper<Tag> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(Tag::getArticleId, articleId);
        tagMapper.delete(queryWrapper3);
        return RetMsg.success(null);
    }
    
    @Override
    public PageInfoParams getArticleByFilingId(FilingPageParams params, boolean isTags, boolean isAuthor,
                                               boolean needContent) {
        Page<Filing> page = new Page<>(params.getPageNumber(), params.getPageSize());
        LambdaQueryWrapper<Filing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Filing::getFilingId, params.getFilingId());
        Page<Filing> filingPage = filingMapper.selectPage(page, queryWrapper);
        List<Filing> records = filingPage.getRecords();
        List<Article> articles = new ArrayList<>();
        for (Filing filing : records) {
            LambdaQueryWrapper<Article> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Article::getArticleId, filing.getArticleId());
            Article article = articleMapper.selectOne(queryWrapper1);
            articles.add(article);
        }
        List<ArticleVo> articleVos = copyList(articles, true, true, false);
        PageInfoParams pageInfoParams = new PageInfoParams();
        pageInfoParams.setArticleVos(articleVos);
        pageInfoParams.setPageNumber(params.getPageNumber());
        pageInfoParams.setPageSize((int) page.getSize());
        pageInfoParams.setTotal((int) page.getTotal());
        pageInfoParams.setCurrentPage((int) page.getCurrent());
        return pageInfoParams;
    }
    
    @Override
    public List<TimeLineParams> time() {
        List<DataYearMonth> timeStrings = articleMapper.selectAllGroupByTime();
        List<TimeLineParams> params = new ArrayList<>();
        for (DataYearMonth timeString : timeStrings) {
            List<Article> articles = articleMapper.selectAllByMonth(timeString);
            List<ArticleVo> articleVos = copyList(articles, false, false, false);
            params.add(new TimeLineParams(timeString, articleVos));
        }
        return params;
    }
    
    @Override
    public int save(String articleId, String content) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, articleId).
                select(Article::getArticleId);
        Article article = articleMapper.selectOne(queryWrapper);
        LambdaUpdateWrapper<Content> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Content::getArticleId, articleId);
        Content content1 = new Content();
//        content1.setArticleId(articleId);
        content1.setContent(content);
        return contentMapper.update(content1, updateWrapper);
    }
    
    @Override
    public List<ArticleVo> getArticleVoByTagId(String tagId) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getTagId, tagId);
        List<ArticleVo> articleVos = new ArrayList<>();
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        for (Tag tag : tags) {
            String articleId = tag.getArticleId();
            LambdaQueryWrapper<Article> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Article::getArticleId, articleId);
            Article article = articleMapper.selectOne(queryWrapper1);
            ArticleVo copy = copy(article, false, false, false);
            articleVos.add(copy);
            
        }
        return articleVos;
    }
    
    @Override
    public Map<String, Object> get(PageParams params) {
        Page<Article> page = new Page<>(params.getPageNumber(), params.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();
        List<ArticleGetParams> paramsList = new ArrayList<>();
        for (Article record : records) {
            List<TagName> tagNames = tagMapper.getTagNameByArticleId(record.getArticleId());
            FilingName filingName = filingMapper.getFilingNameByArticleId(record.getArticleId());
            paramsList.add(new ArticleGetParams(record, tagNames, filingName));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paramsList", paramsList);
        map.put("pageSize", page.getSize());
        map.put("currentPage", page.getCurrent());
        map.put("total", page.getTotal());
        return map;
    }
    
    @Override
    public List<ArticleVo> getArticleVosByFilingId(Integer id) {
        LambdaQueryWrapper<Filing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Filing::getFilingId, id);
        List<Filing> articleIds = filingMapper.selectList(queryWrapper);
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Filing articleId : articleIds) {
            String articleId1 = articleId.getArticleId();
            LambdaQueryWrapper<Article> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(Article::getArticleId, articleId1);
            Article article = articleMapper.selectOne(queryWrapper1);
            ArticleVo copy = copy(article, false, false, false);
            articleVos.add(copy);
        }
        return articleVos;
    }
    
    @Override
    public int setTop(String articleId) {
        Article article = new Article();
        article.setSetTop(false);
        articleMapper.update(article, null);
        Article setTop = new Article();
        setTop.setArticleId(articleId);
        setTop.setSetTop(true);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getArticleId, articleId);
        return articleMapper.update(setTop, queryWrapper);
    }
    
    @Override
    public ArticleVo lastEdit() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getUpdateTime);
        queryWrapper.eq(Article::getDeleted, false);
        queryWrapper.last("limit 1");
        Article article = articleMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<Content> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Content::getArticleId, article.getArticleId());
        ArticleVo copy = copy(article, false, false, true);
        return copy;
    }
    
    @Override
    public int like(String articleId) {
        return articleMapper.likeAdd(articleId);
    }
    
    @Override
    public Map<String, Object> deletedArticle(PageParams params) {
        PageHelper.startPage(params.getPageNumber(), params.getPageSize());
        List<Article> articles = articleMapper.selectDeletedArticleList();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<ArticleGetParams> paramsList = new ArrayList<>();
        for (Article article : articles) {
            paramsList.add(new ArticleGetParams(article, null, null));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("articles", paramsList);
        map.put("total", pageInfo.getTotal());
        map.put("size", pageInfo.getSize());
        map.put("current", pageInfo.getPageNum());
        return map;
    }
    
    @Override
    public int recoverArticle(String articleId) {
        return articleMapper.recoverArticle(articleId);
    }
}