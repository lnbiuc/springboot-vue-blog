package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Content
 * date: 2022/6/3 18:24
 *
 * @author ayanamirei
 */

@Data
public class Content implements Serializable
{
    @TableId
    private String articleId;
    private String content;
}
