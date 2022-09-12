package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.Filing;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.mapper.ArticleMapper;
import top.beyondhorizon.mapper.FilingMapper;
import top.beyondhorizon.mapper.FilingNameMapper;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.FilingArticleVos;
import top.beyondhorizon.model.params.FilingPageParams;
import top.beyondhorizon.model.params.PageInfoParams;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.vo.ArticleFilingVo;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.model.vo.FilingArticleVo;
import top.beyondhorizon.service.ArticleService;
import top.beyondhorizon.service.FilingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: FilingServiceImpl
 * date: 2022/6/10 00:10
 *
 * @author ayanamirei
 */


@Service
@Slf4j
public class FilingServiceImpl implements FilingService {
    private final FilingNameMapper filingNameMapper;
    
    private final FilingMapper filingMapper;
    
    private final ArticleService articleService;
    
    private final ArticleMapper articleMapper;
    
    public FilingServiceImpl(FilingMapper filingMapper, FilingNameMapper filingNameMapper,
                             ArticleService articleService, ArticleMapper articleMapper) {
        this.filingMapper = filingMapper;
        this.filingNameMapper = filingNameMapper;
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }
    
    /**
     * 删除一个标签
     *
     * @param filingId id
     * @return re
     */
    @Override
    public int deleteTag(Integer filingId) {
        LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FilingName::getId, filingId).last("limit 1");
        return filingNameMapper.delete(queryWrapper);
    }
    
    /**
     * 获取所有分类名
     *
     * @return re
     */
    @Override
    public List<FilingName> allFiling() {
        return filingNameMapper.selectList(null);
    }
    
    /**
     * 获取所有文件夹名以及文件夹下的文件
     *
     * @return re
     */
    @Override
    public List<FilingArticleVo> filing() {
        List<Integer> filings = filingMapper.selectFilingId();
        List<FilingArticleVo> filingArticleVos = new ArrayList<>();
        for (Integer filing : filings) {
            FilingPageParams params = new FilingPageParams(1, 999, filing);
            PageInfoParams articles = articleService.getArticleByFilingId(params, false, false, false);
            List<ArticleFilingVo> articleFilingVos = copyList(articles);
            LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(FilingName::getId, filing);
            String filingName = filingNameMapper.selectOne(queryWrapper).getFilingName();
            filingArticleVos.add(new FilingArticleVo(filingName, filing, articleFilingVos));
        }
        return filingArticleVos;
    }
    
    private List<ArticleFilingVo> copyList(PageInfoParams articles) {
        List<ArticleVo> articleVos = articles.getArticleVos();
        
        List<ArticleFilingVo> articleFilingVoList = new ArrayList<>();
        for (ArticleVo articleVo : articleVos) {
            ArticleFilingVo articleFilingVos = new ArticleFilingVo();
            BeanUtils.copyProperties(articleVo, articleFilingVos);
            articleFilingVoList.add(articleFilingVos);
        }
        
        return articleFilingVoList;
    }
    
    /**
     * 获取某个文件夹下的所有文章摘要
     *
     * @param params 文章id 分页数据
     * @return 返回
     */
    @Override
    public PageInfoParams get(FilingPageParams params) {
        return articleService.getArticleByFilingId(params, true, true, false);
    }
    
    /**
     * 获取一篇文章的文件夹信息
     *
     * @param articleId 文章id
     * @return re
     */
    @Override
    public FilingName getFilingNameByArticleId(String articleId) {
        LambdaQueryWrapper<Filing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Filing::getArticleId, articleId);
        Filing filing = filingMapper.selectOne(queryWrapper);
        if (filing == null) {
            return null;
        }
        Integer filingId = filing.getFilingId();
        LambdaQueryWrapper<FilingName> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(FilingName::getId, filingId);
        return filingNameMapper.selectOne(queryWrapper1);
    }
    
    @Override
    public Map<String, Object> getArticleFilingInfo(PageParams params) {
        Page<FilingName> page = new Page<>(params.getPageNumber(), params.getPageSize());
        Page<FilingName> pages = filingNameMapper.selectPage(page, null);
        List<FilingName> filingNames = pages.getRecords();
        List<FilingArticleVos> list = new ArrayList<>();
        for (FilingName filingName : filingNames) {
            List<ArticleVo> articleVos = articleService.getArticleVosByFilingId(filingName.getId());
            FilingArticleVos vos = new FilingArticleVos();
            vos.setCount(articleVos.size());
            vos.setFilingName(filingName);
            list.add(vos);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paramsList", list);
        map.put("pageSize", page.getSize());
        map.put("currentPage", page.getCurrent());
        map.put("total", page.getTotal());
        return map;
    }
    
    /**
     * 将某个文章移除分类
     *
     * @param articleId articleId
     * @return re
     */
    @Override
    public int remove(String articleId) {
        LambdaQueryWrapper<Filing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Filing::getArticleId, articleId);
        return filingMapper.delete(queryWrapper);
    }
    
    /**
     * 获取没有分类的文章
     *
     * @return re
     */
    @Override
    public List<Article> getUnFiling() {
        List<Article> articles = filingMapper.selectUnFilingArticle();
        return articles;
    }
    
    /**
     * 重命名某个文件夹
     *
     * @param filingId   该文件夹id
     * @param filingName 新名字
     * @return re
     */
    @Override
    public int reName(Integer filingId, String filingName) {
        FilingName filing = new FilingName(null, filingName);
        LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FilingName::getId, filingId);
        return filingNameMapper.update(filing, queryWrapper);
    }
    
    /**
     * 新建分类
     *
     * @param filingName filingName
     * @return re
     */
    @Override
    public RetMsg add(String filingName) {
        if (filingName.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FilingName::getFilingName, filingName);
        List<FilingName> filingNames = filingNameMapper.selectList(queryWrapper);
        if (filingNames.size() > 0) {
            return RetMsg.fail(ErrorCode.FILING_EXISTS.getCode(),
                    ErrorCode.FILING_EXISTS.getMsg());
        }
        int i = filingNameMapper.insert(new FilingName(null, filingName));
        if (i < 1) {
            return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                    ErrorCode.FILING_EXISTS.getMsg());
        }
        return RetMsg.success(null);
    }
    
    /**
     * 删除一个分类目录，同时删除这个目录引用的文章
     *
     * @param filingId filingId
     * @return re
     */
    @Override
    public int deleteFiling(String filingId) {
        LambdaQueryWrapper<Filing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Filing::getFilingId, filingId);
        filingMapper.delete(queryWrapper);
        LambdaQueryWrapper<FilingName> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(FilingName::getId, filingId);
        return filingNameMapper.delete(queryWrapper1);
    }
    
    /**
     * 为某一篇文章设置分类
     *
     * @param articleId 文章id
     * @return re
     */
    @Override
    public RetMsg set(String articleId, String filingName) {
        if (articleId == null || articleId.equals("") || filingName == null || filingName.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        LambdaQueryWrapper<Article> q = new LambdaQueryWrapper<>();
        q.eq(Article::getArticleId, articleId);
        Article article = articleMapper.selectOne(q);
        if (article == null) {
            return RetMsg.fail(ErrorCode.ARTICLE_NOT_EXISTS.getCode(),
                    ErrorCode.ARTICLE_NOT_EXISTS.getMsg());
        }
        LambdaQueryWrapper<FilingName> q3 = new LambdaQueryWrapper<>();
        q3.eq(FilingName::getFilingName, filingName);
        FilingName exist = filingNameMapper.selectOne(q3);
        if (exist == null) {
            FilingName notExist = new FilingName(null, filingName);
            int i = filingNameMapper.insert(notExist);
            if (i < 1) {
                return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                        ErrorCode.UPDATE_FILE.getMsg());
            }
            LambdaQueryWrapper<FilingName> q4 = new LambdaQueryWrapper<>();
            q4.eq(FilingName::getFilingName, filingName);
            exist = filingNameMapper.selectOne(q4);
        }
        Filing filing = new Filing(articleId, exist.getId());
        int i = filingMapper.insert(filing);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @Override
    public FilingName getFilingNameByFilingId(Integer filingId) {
        LambdaQueryWrapper<FilingName> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FilingName::getId, filingId);
        FilingName filingName = filingNameMapper.selectOne(queryWrapper);
        return filingName;
    }
}
