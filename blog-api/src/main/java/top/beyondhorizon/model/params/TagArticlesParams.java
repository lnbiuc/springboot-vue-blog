package top.beyondhorizon.model.params;

import lombok.Data;
import top.beyondhorizon.entity.TagName;
import top.beyondhorizon.model.vo.ArticleVo;

import java.util.List;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: TagArticlesParams
 * @create 2022-06-18 17:42
 */

@Data
public class TagArticlesParams
{
    private TagName tagName;

    private List<ArticleVo> articleVos;
}
