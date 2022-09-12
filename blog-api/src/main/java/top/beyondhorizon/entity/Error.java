package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: Error
 * date: 2022/9/10 18:52
 *
 * @author ayanamirei
 */

@Data
public class Error {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String eMsg;
    
    private String stackTrace;
    
    private Date timestamp;
}
