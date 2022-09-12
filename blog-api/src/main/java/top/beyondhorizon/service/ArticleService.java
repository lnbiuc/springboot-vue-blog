package top.beyondhorizon.service;


import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.*;
import top.beyondhorizon.model.vo.ArticleVo;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ArticleService
 * date: 2022/5/30 18:20
 *
 * @author ayanamirei
 */


public interface ArticleService {
    
    PageInfoParams getLastUpdateArticle(PageParams pageParams, boolean isTags, boolean isAuthor, boolean needContent);
    
    ArticleVo getArticleById(String articleId);
    
    RetMsg publishArticle(PublishParams publishParams, String token);
    
    RetMsg edit(EditParams editParams, String token);
    
    RetMsg delete(String articleId, String token);
    
    PageInfoParams getArticleByFilingId(FilingPageParams params, boolean isTags, boolean isAuthor, boolean needContent);
    
    List<TimeLineParams> time();
    
    int save(String articleId, String content);
    
    List<ArticleVo> getArticleVoByTagId(String tagId);
    
    Map<String, Object> get(PageParams params);
    
    List<ArticleVo> getArticleVosByFilingId(Integer id);
    
    int setTop(String articleId);
    
    ArticleVo lastEdit();
    
    int like(String articleId);
    
    Map<String, Object> deletedArticle(PageParams params);
    
    int recoverArticle(String articleId);
}
