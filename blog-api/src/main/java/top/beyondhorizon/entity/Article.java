package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Article implements Serializable
{
    /**
     * 文章id
     */
    @TableId
    private String articleId;
    
    /**
     * 作者id
     */
    private String authorId;
    
    /**
     * 文章标题
     */
    private String title;
    
    /**
     * 文章简介
     */
    private String introduction;
    
    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date releaseTime;
    
    /**
     * 最后一次更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    
    /**
     * 是否置顶 默认0 ，1置顶
     */
    private Boolean setTop;
    
    /**
     * 是否删除，隐藏 默认0 ，1隐藏
     */
    @TableLogic
    private Boolean deleted;
    
    /**
     * 点击量
     */
    private Integer visitsCount;
    
    /**
     * 点赞量
     */
    private Integer likeCount;
    
    
    private String bgImg;
}

