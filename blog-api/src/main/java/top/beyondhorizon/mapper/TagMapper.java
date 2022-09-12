package top.beyondhorizon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.beyondhorizon.entity.Tag;
import top.beyondhorizon.entity.TagName;

import java.util.List;

/**
 * ClassName: TagMapper
 * date: 2022/5/30 18:33
 *
 * @author ayanamirei
 */

@Repository
public interface TagMapper extends BaseMapper<Tag>
{
    List<TagName> getTagNameByArticleId(@Param("articleId") String articleId);

}
