package top.beyondhorizon.service;

import top.beyondhorizon.entity.Article;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.SearchParams;

import java.util.List;

/**
 * ClassName: SearchService
 * date: 2022/7/2 03:10
 *
 * @author ayanamirei
 */


public interface SearchService {
    List<Article> searchArticleByTag(String tag);
    
    SearchParams searchArticleByTagId(Integer tagId);
    
    SearchParams searchArticleByFilingName(String filingName);
    
    SearchParams searchArticleByFilingId(Integer filingId);
    
    SearchParams searchArticleByTitle(String title);
}
