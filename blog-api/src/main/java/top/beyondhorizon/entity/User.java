package top.beyondhorizon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable
{
    /**
     * 用户id
     */
    @TableId
    private String id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 权限等级 默认0
     */
    private Boolean permission;
    
    /**
     * 手机号
     */
    private String tel;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 头像
     */
    private String headPortrait;
    
    /**
     * 密码盐
     */
    private String salt;
    
    /**
     * 是否删除 默认0
     */
    @TableLogic
    private Boolean deleted;
    
    /**
     * 注册时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date regTime;
    
    /**
     * 最后一次登陆时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date lastLogin;
}

