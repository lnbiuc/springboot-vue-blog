package top.beyondhorizon.service;


import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.FilingPageParams;
import top.beyondhorizon.model.params.PageInfoParams;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.vo.FilingArticleVo;

import java.util.List;
import java.util.Map;

/**
 * ClassName: FilingService
 * date: 2022/6/10 00:10
 *
 * @author ayanamirei
 */


public interface FilingService {
    
    int deleteTag(Integer filingId);
    
    List<FilingName> allFiling();
    
    List<FilingArticleVo> filing();
    
    PageInfoParams get(FilingPageParams params);
    
    FilingName getFilingNameByArticleId(String articleId);
    
    Map<String, Object> getArticleFilingInfo(PageParams params);
    
    int remove(String articleId);
    
    List<Article> getUnFiling();
    
    int reName(Integer filingId, String filingName);
    
    RetMsg add(String filingName);
    
    int deleteFiling(String filingId);
    
    RetMsg set(String articleId, String filingName);
    
    FilingName getFilingNameByFilingId(Integer filingId);
}
