package top.beyondhorizon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.TagName;

import java.util.List;

/**
 * ClassName: TagNameMapper
 * date: 2022/5/30 22:19
 *
 * @author ayanamirei
 */

@Repository
public interface TagNameMapper extends BaseMapper<TagName>
{
    List<Article> selectArticleByTagName(String tag);
    
    List<Article> selectArticleByTagId(Integer tagId);
    
    List<TagName> selectInUseTagName();
}
