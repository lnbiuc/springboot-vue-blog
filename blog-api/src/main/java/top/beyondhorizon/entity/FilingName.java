package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: FilingName
 * date: 2022/6/10 00:08
 *
 * @author ayanamirei
 */


@Data
@AllArgsConstructor
public class FilingName implements Serializable
{
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String filingName;
}
