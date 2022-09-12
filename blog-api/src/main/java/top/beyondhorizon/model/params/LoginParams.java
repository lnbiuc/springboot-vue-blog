package top.beyondhorizon.model.params;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: LoginParams
 * date: 2022/5/31 20:01
 *
 * @author ayanamirei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginParams implements Serializable
{
    private String username;
    
    private String password;
}
