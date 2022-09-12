package top.beyondhorizon.model.vo;


import lombok.Data;

/**
 * ClassName: TagVo
 * date: 2022/5/30 21:44
 *
 * @author ayanamirei
 */

@Data
public class TagVo
{
    /**
     * 文章的tag-id
     */
    private String tagId;
    
    /**
     * tag名字
     */
    private String tagName;
    
    /**
     * 标签被引用次数
     */
    private Integer count;
}
