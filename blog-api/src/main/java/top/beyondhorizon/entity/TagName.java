package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagName implements Serializable
{
    /**
     * tagid
     */
    @TableId(type = IdType.AUTO)
    private String tagId;
    
    /**
     * tag名字
     */
    private String tagName;
}

