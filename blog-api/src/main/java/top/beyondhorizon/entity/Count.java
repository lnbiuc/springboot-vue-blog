package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: TotalView
 * @create 2022-06-24 23:46
 */

@Data
public class Count
{
    @TableId
    private Integer totalView;
}
