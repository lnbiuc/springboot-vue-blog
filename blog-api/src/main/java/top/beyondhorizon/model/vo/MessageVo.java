package top.beyondhorizon.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: MessageVo
 * date: 2022/9/7 18:00
 *
 * @author ayanamirei
 */

@Data
public class MessageVo {
    
    /**
     * 评论id
     */
    private Integer id;
    
    /**
     * 评论昵称
     */
    private String nickname;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 地区
     */
    private String ip;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date time;
}
