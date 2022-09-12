package top.beyondhorizon.model.params;

import lombok.Data;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.model.vo.ArticleVo;

import java.io.Serializable;
import java.util.List;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: FilingArticelVos
 * @create 2022-06-21 20:49
 */

@Data
public class FilingArticleVos implements Serializable
{
    private FilingName filingName;

    private Integer count;
}
