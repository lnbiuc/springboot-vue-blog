package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag
{
    /**
     * 文章id
     */
    @TableId
    private String articleId;
    
    /**
     * 文章的tag-id
     */
    private String tagId;
}

