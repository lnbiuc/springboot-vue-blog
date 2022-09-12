package top.beyondhorizon.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: AuthorVo
 * date: 2022/5/30 20:55
 *
 * @author ayanamirei
 */

@Data
public class AuthorVo
{
    private String id;
    private String username;
    private String email;
    private String headPortrait;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date lastLogin;
}
