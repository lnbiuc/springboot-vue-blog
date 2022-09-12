package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments implements Serializable
{
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 发表时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createDate;
    
    /**
     * 文章id
     */
    private String articleId;
    
    /**
     * 发表该评论的作者id
     */
    private String authorId;
    
    /**
     * 这条评论是给谁的评论评论的
     */
    private Integer parentId;
    
    /**
     * 给谁评论的
     */
    private String toId;
    
    /**
     * 等级
     */
    private Integer level;
    
}

