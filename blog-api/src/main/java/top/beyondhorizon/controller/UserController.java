package top.beyondhorizon.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.LoginParams;
import top.beyondhorizon.model.params.SignupParams;
import top.beyondhorizon.model.vo.UserVo;
import top.beyondhorizon.service.LoginService;
import top.beyondhorizon.service.TokenService;

/**
 * ClassName: UserController
 * date: 2022/5/30 21:27
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    
    private final LoginService loginService;
    private final TokenService tokenService;
    
    public UserController(LoginService loginService, TokenService tokenService) {
        this.loginService = loginService;
        this.tokenService = tokenService;
    }
    
    @LogAnnotation(module = "用户", operation = "登陆")
    @PostMapping("login")
    public RetMsg login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }
    
    @LogAnnotation(module = "用户", operation = "获取当前用户")
    @PostMapping("status")
    public RetMsg status(@RequestHeader String token) {
        UserVo status = tokenService.status(token);
        if (status == null) {
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(),
                    ErrorCode.NOT_LOGIN.getMsg());
        }
        return RetMsg.success(status);
    }
    
    @LogAnnotation(module = "用户", operation = "退出登陆")
    @PostMapping("logout")
    public RetMsg logout(@RequestHeader(required = false) String token) {
        return loginService.logout(token);
    }
    
    @LogAnnotation(module = "用户", operation = "注册")
    @PostMapping("signup")
    public RetMsg signup(@RequestBody SignupParams signupParams) {
        return loginService.signup(signupParams);
    }
    
    @LogAnnotation(module = "用户", operation = "发送验证码")
    @PostMapping("code")
    public RetMsg code(@RequestParam String tel) {
        return loginService.code(tel);
    }
}
