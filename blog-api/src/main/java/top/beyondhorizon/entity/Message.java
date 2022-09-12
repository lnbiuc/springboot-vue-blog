package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Message
 * date: 2022/9/7 17:54
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    
    /**
     * 评论id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * qq号
     */
    private String authorId;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 评论时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    
    /**
     * 评论ip地址
     */
    private String ip;
    
    /**
     * ip地址解析的地区
     */
    private String region;
}
