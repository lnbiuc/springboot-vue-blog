package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: MessageAuthor
 * date: 2022/9/7 17:58
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageAuthor implements Serializable {
    
    /**
     * 评论qq号
     */
    @TableId(type = IdType.AUTO)
    private String qq;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像url
     */
    private String avatar;
    
}
