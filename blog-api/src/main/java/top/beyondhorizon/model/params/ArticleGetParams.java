package top.beyondhorizon.model.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.entity.TagName;

import java.util.List;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: ArticleGetParams
 * @create 2022-06-19 22:14
 */

@Data
@AllArgsConstructor
public class ArticleGetParams
{
    private Article article;

    private List<TagName> tagNames;

    private FilingName filingName;

}
