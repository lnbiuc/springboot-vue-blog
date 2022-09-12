package top.beyondhorizon.service.impl;


import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.dto.UserDto;
import top.beyondhorizon.model.params.LoginParams;
import top.beyondhorizon.model.params.LoginRetParams;
import top.beyondhorizon.model.params.SignupParams;
import top.beyondhorizon.entity.User;
import top.beyondhorizon.model.vo.UserVo;
import top.beyondhorizon.service.LoginService;
import top.beyondhorizon.service.TokenService;
import top.beyondhorizon.service.UserService;
import top.beyondhorizon.utils.JWTUtils;
import top.beyondhorizon.utils.SendSmsUtil;
import top.beyondhorizon.utils.UuidBuilder;
import top.beyondhorizon.utils.VerificationCode;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: LoginServiceImpl
 * date: 2022/5/31 19:59
 *
 * @author ayanamirei
 */


@Service
@Slf4j
public class LoginServiceImpl implements LoginService
{

    private final UserService userService;
    private final TokenService tokenService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public LoginServiceImpl(UserService userService, TokenService tokenService)
    {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public RetMsg login(LoginParams loginParams)
    {
        String username = loginParams.getUsername();
        String password = loginParams.getPassword();
        if (!check(username, password))
        {
            return RetMsg.fail(ErrorCode.ACCOUNT_PWD_Illegal.getCode(),
                    ErrorCode.ACCOUNT_PWD_Illegal.getMsg());
        }
        Subject subject = SecurityUtils.getSubject();
        log.warn("用户-{}-开始执行登陆" + username);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        token.setRememberMe(true);
        try
        {
            subject.login(token);
            UserDto currentUser = (UserDto) subject.getPrincipal();
            log.warn("currentUser---{}", currentUser);
            String savedToken = JWTUtils.createToken(currentUser.getId());
            currentUser.setPassword(null);
            redisTemplate.opsForValue().set("TOKEN_" + savedToken, JSON.toJSONString(currentUser), 1, TimeUnit.DAYS);
            if (userService.updateLastLogin(currentUser.getId()))
            {
                log.warn("更新用户最后登陆时间+{}+{}", username, new Date());
            }

            log.warn("用户登陆成功:{}" + username);
            UserVo userVo = tokenService.getUserByToken(savedToken);
            LoginRetParams params = new LoginRetParams();
            params.setToken(savedToken);
            params.setUserVo(userVo);
            return RetMsg.success(params);
        } catch (UnknownAccountException e)
        {
            //账号不存在
            log.warn("登陆异常 账户不存在");
            return RetMsg.fail(ErrorCode.ACCOUNT_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_NOT_EXIST.getMsg());
        } catch (IncorrectCredentialsException e)
        {
            //密码不正确
            log.warn("登陆异常，密码错误");
            return RetMsg.fail(ErrorCode.PWD_ERROR.getCode(), ErrorCode.PWD_ERROR.getMsg());
        } catch (ConcurrentAccessException e)
        {
            //一个账户同时只允许一个用户登陆
            log.warn("登陆异常，已经登陆");
            return RetMsg.fail(ErrorCode.HAS_LOGIN.getCode(),
                    ErrorCode.HAS_LOGIN.getMsg());
        } catch (AuthenticationException e)
        {
            return RetMsg.fail(ErrorCode.ACCOUNT_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_NOT_EXIST.getMsg());

        }
    }

    private boolean check(String username, String password)
    {
        Pattern unamePattern = Pattern.compile("^\\w+${4,18}");
        Matcher unameMatcher = unamePattern.matcher(username);
        Pattern pwdPattern = Pattern.compile("^\\w+${6,36}");
        Matcher pwdMatcher = pwdPattern.matcher(password);
        return unameMatcher.matches() && pwdMatcher.matches();
    }

    @Override
    public RetMsg logout(String token)
    {
        if (token.equals(""))
        {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("TOKEN_" + token)))
        {
            log.warn("未能在redis中获取到key");
            return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg());
        }
        if (Boolean.TRUE.equals(redisTemplate.delete("TOKEN_" + token)))
        {
            log.warn("退出登陆成功");
            return RetMsg.success("退出成功");
        }
        return RetMsg.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg());
    }

    private boolean checkTel(String tel)
    {
        Pattern pattern = Pattern.compile("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        Matcher m = pattern.matcher(tel);
        return m.matches();
    }

    @Override
    public RetMsg signup(SignupParams signupParams)
    {
        String username = signupParams.getUsername();
        String password = signupParams.getPassword();
        String tel = signupParams.getTel();
        String code = signupParams.getCode();
        log.warn("开始执行用户注册，{}", username);
        //检查提交信息是否为空
        log.warn("检查注册信息完整性");
        if (username == null || password == null || tel == null || code == null)
        {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        log.warn("检查注册信息格式");
        //检查用户名密码格式是否正确
        if (!check(username, password))
        {
            return RetMsg.fail(ErrorCode.ACCOUNT_PWD_Illegal.getCode(),
                    ErrorCode.ACCOUNT_PWD_Illegal.getMsg());
        }
        log.warn("检查注册手机号格式");
        //检查手机号格式是否正确
        if (!checkTel(tel))
        {
            return RetMsg.fail(ErrorCode.PHONE_NUMBER_ILLEGAL.getCode(),
                    ErrorCode.PHONE_NUMBER_ILLEGAL.getMsg());
        }
        log.warn("检查注册手机号是否已经被注册");
        //检查手机号是否被注册
        if (userService.getUserByTel(tel) != null)
        {
            return RetMsg.fail(ErrorCode.PHONE_NUMBER_REGISTERED.getCode(),
                    ErrorCode.PHONE_NUMBER_REGISTERED.getMsg());
        }
        log.warn("检查用户名是否已经被注册");
        //检查用户名是否被注册
        if (userService.getUserByUsernameReUser(username) != null)
        {
            return RetMsg.fail(ErrorCode.ACCOUNT_REGISTERED.getCode(),
                    ErrorCode.ACCOUNT_REGISTERED.getMsg());
        }
        log.warn("检查是否已经发送验证码");
        //检查是否发送过验证码
        if (Boolean.FALSE.equals(redisTemplate.hasKey(tel)))
        {
            return RetMsg.fail(ErrorCode.VERIFICATION_CODE_NOT_SENT.getCode(),
                    ErrorCode.VERIFICATION_CODE_NOT_SENT.getMsg());
        }
        log.warn("检查验证码是否匹配");
        //获取验证码检查验证码是否匹配
        String redisCode = redisTemplate.opsForValue().get(tel);
        if (!code.equals(redisCode))
        {
            return RetMsg.fail(ErrorCode.VERIFICATION_CODE_ERROR.getCode(),
                    ErrorCode.VERIFICATION_CODE_ERROR.getMsg());
        }
        //生成uuid
        String uuid = UuidBuilder.getUuid();
        //生成密码加密盐
        String salt = UuidBuilder.getUuid();
        //密码加密
        String encodePwd = DigestUtils.md5Hex(salt + password);
        //生成注册时间
        Date date = new Date();
        //保存信息
        int i = userService.saveUser(uuid, username, encodePwd, tel, salt, date);
        log.warn("执行注册");
        if (i > 0)
        {
            return RetMsg.success("注册成功");
        }
        return RetMsg.error("注册失败");
    }

    @Override
    public RetMsg code(String tel)
    {
        //检查手机号格式
        log.warn("开始验证手机号+{}", tel);
        if (!checkTel(tel))
        {
            return RetMsg.fail(ErrorCode.PHONE_NUMBER_ILLEGAL.getCode(),
                    ErrorCode.PHONE_NUMBER_ILLEGAL.getMsg());
        }
        //检查手机号是否被注册
        log.warn("检查手机号是否被注册+{}", tel);
        User user = userService.getUserByTel(tel);
        if (user != null)
        {
            return RetMsg.fail(ErrorCode.PHONE_NUMBER_REGISTERED.getCode(),
                    ErrorCode.PHONE_NUMBER_REGISTERED.getMsg());
        }
        //检查是否已经发送过验证码,让用户60秒后再发送
        log.warn("检查验证码是否已经发送");
        if (Boolean.TRUE.equals(redisTemplate.hasKey(tel)))
        {
            return RetMsg.fail(ErrorCode.ALREADY_SENT_CODE.getCode(),
                    ErrorCode.ALREADY_SENT_CODE.getMsg());
        }
        //生成验证码
        String code = VerificationCode.getCode();
        log.warn("发送验证码+{}", tel);
        //发送验证码
        String status = SendSmsUtil.SendSMS(code, tel);
        if ("Ok".equals(status))
        {
            //将验证码保存到redis 设置过期时间为60s
            redisTemplate.opsForValue().set(tel, code, 1, TimeUnit.MINUTES);
            log.warn("验证码发送成功+{}+{}", tel, code);
            return RetMsg.success("发送成功");
        }
        return RetMsg.fail(ErrorCode.SENDFILE.getCode(),
                ErrorCode.SENDFILE.getMsg());
    }
}
