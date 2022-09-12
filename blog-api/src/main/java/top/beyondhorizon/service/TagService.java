package top.beyondhorizon.service;

import top.beyondhorizon.entity.TagName;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.vo.TagVo;

import java.util.List;
import java.util.Map;

/**
 * ClassName: TagService
 * date: 2022/5/30 21:30
 *
 * @author ayanamirei
 */


public interface TagService {
    
    List<TagVo> getAllTag();
    
    int addTag(String tagName);
    
    Map<String, Object> getTag(PageParams params);
    
    int delete(String tagId);
    
    int edit(String tagId, String tagName);
    
    List<TagName> getTagByArticleId(String articleId);
}
