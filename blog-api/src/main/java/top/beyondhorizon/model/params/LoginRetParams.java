package top.beyondhorizon.model.params;


import lombok.Data;
import top.beyondhorizon.model.vo.UserVo;

import java.io.Serializable;

/**
 * ClassName: LoginRetParams
 * date: 2022/6/7 20:12
 *
 * @author ayanamirei
 */

@Data
public class LoginRetParams implements Serializable
{
    private String token;
    private UserVo userVo;
}
