package top.beyondhorizon.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.entity.TagName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: ArticleVo
 * date: 2022/5/30 18:18
 *
 * @author ayanamirei
 */

@Data
public class ArticleVo implements Serializable
{
    /**
     * 文章id
     */
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
     * 文章内容
     */
    private String content;
    
    /**
     * 标签
     */
    private List<TagName> tagNames;
    
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
     * 作者信息
     */
    private AuthorVo authorVo;
    
    /**
     * 点击量
     */
    private Integer visitsCount;
    
    /**
     * 点赞量
     */
    private Integer likeCount;
    
    /*
    归档的文件夹
     */
    private FilingName filingName;
    
    
    private String bgImg;
}
