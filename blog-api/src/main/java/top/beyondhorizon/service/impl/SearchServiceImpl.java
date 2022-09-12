package top.beyondhorizon.service.impl;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.mapper.ArticleMapper;
import top.beyondhorizon.mapper.FilingNameMapper;
import top.beyondhorizon.mapper.TagNameMapper;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.SearchParams;
import top.beyondhorizon.model.vo.ArticleVo;
import top.beyondhorizon.service.ArticleService;
import top.beyondhorizon.service.SearchService;

import java.util.List;

/**
 * ClassName: SearchServiceImpl
 * date: 2022/7/2 03:10
 *
 * @author ayanamirei
 */


@Service
public class SearchServiceImpl implements SearchService {
    private final TagNameMapper tagNameMapper;
    
    public SearchServiceImpl(TagNameMapper tagNameMapper, FilingNameMapper filingNameMapper, ArticleServiceImpl articleService, ArticleMapper articleMapper) {
        this.tagNameMapper = tagNameMapper;
        this.filingNameMapper = filingNameMapper;
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }
    
    private final FilingNameMapper filingNameMapper;
    private final ArticleServiceImpl articleService;
    
    private final ArticleMapper articleMapper;
    
    @Override
    public List<Article> searchArticleByTag(String tag) {
        return tagNameMapper.selectArticleByTagName(tag);
    }
    
    @Override
    public SearchParams searchArticleByTagId(Integer tagId) {
        List<Article> articles = tagNameMapper.selectArticleByTagId(tagId);
        return copyAndRet(articles);
    }
    
    @Override
    public SearchParams searchArticleByFilingName(String filingName) {
        
        List<Article> articles = filingNameMapper.selectArticleByFilingName(filingName);
        return copyAndRet(articles);
    }
    
    @Override
    public SearchParams searchArticleByFilingId(Integer filingId) {
        List<Article> articles = filingNameMapper.selectArticleByFilingId(filingId);
        return copyAndRet(articles);
    }
    
    @NotNull
    private SearchParams copyAndRet(List<Article> articles) {
        SearchParams params = new SearchParams();
        int total = articles.size();
        params.setTotal(articles.size());
        if (total > 0) {
            List<ArticleVo> articleVos = articleService.copyList(articles, true, true, false);
            params.setData(articleVos);
            return params;
        }
        return params;
    }
    
    @Override
    public SearchParams searchArticleByTitle(String title) {
        List<Article> articles = articleMapper.selectArticleByTitle(title);
        return copyAndRet(articles);
    }
}
