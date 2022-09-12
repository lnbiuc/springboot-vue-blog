package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: Filing
 * date: 2022/6/10 00:06
 *
 * @author ayanamirei
 */


@Data
@AllArgsConstructor
public class Filing implements Serializable
{
    @TableId(type = IdType.AUTO)
    private String articleId;
    
    private Integer filingId;
}
