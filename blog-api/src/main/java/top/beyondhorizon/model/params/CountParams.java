package top.beyondhorizon.model.params;

import lombok.Data;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: CountParams
 * @create 2022-06-24 22:50
 */

@Data
public class CountParams
{
    private Integer totalViews;

    private Integer existTime;

    private Integer totalBlogs;

    private Integer totalTags;
}
