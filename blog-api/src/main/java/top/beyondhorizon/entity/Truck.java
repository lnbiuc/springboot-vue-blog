package top.beyondhorizon.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Period;
import java.util.Date;

/**
 * ClassName: Truck
 * date: 2022/9/8 14:24
 *
 * @author ayanamirei
 */

@Data
public class Truck {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 模块
     */
    private String module;
    
    /**
     * 操作
     */
    private String operation;
    
    /**
     * 执行的方法
     */
    private String method;
    
    /**
     * 请求Ip
     */
    private String ip;
    
    /**
     * 方法执行时间
     */
    private String executeTime;
    
    /**
     * 请求浏览器
     */
    private String brName;
    
    /**
     * 请求操作系统
     */
    private String osName;
    
    /**
     * 请求时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date time;
}
