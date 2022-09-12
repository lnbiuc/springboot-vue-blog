package top.beyondhorizon.model.params;


import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: SignupParams
 * date: 2022/6/1 15:25
 *
 * @author ayanamirei
 */

@Data
public class SignupParams implements Serializable
{
    private String username;
    private String password;
    private String tel;
    private String code;
}
