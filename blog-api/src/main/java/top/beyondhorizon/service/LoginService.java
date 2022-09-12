package top.beyondhorizon.service;

import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.LoginParams;
import top.beyondhorizon.model.params.SignupParams;

/**
 * ClassName: LoginService
 * date: 2022/5/31 19:59
 *
 * @author ayanamirei
 */


public interface LoginService
{
    RetMsg login(LoginParams loginParams);
    
    RetMsg logout(String token);
    RetMsg signup(SignupParams signupParams);
    RetMsg code(String tel);
}
