package top.beyondhorizon.model.params;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: TagAndCount
 * @create 2022-06-19 21:46
 */

@Data
@AllArgsConstructor
public class TagAndCount
{
    private String tagId;

    private String tagName;

    private Integer count;
}
