package top.beyondhorizon.service.impl;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.vo.UserVo;
import top.beyondhorizon.service.TokenService;
import top.beyondhorizon.service.UserService;
import top.beyondhorizon.utils.JWTUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * ClassName: TokerServiceImpl
 * date: 2022/5/31 21:06
 *
 * @author ayanamirei
 */


@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    
    public TokenServiceImpl(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public UserVo getUserByToken(String token) {
        log.warn("开始Token解析");
        UserVo userVo;
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null) {
            return null;
        }
        String userVoJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (userVoJson == null) {
            return null;
        }
        log.warn("userVoJson+{}", userVoJson);
        userVo = JSON.parseObject(userVoJson, UserVo.class);
        if (userVo == null) {
            return null;
        }
        userVo.setHeadPortrait(userService.getUserHead(userVo.getId()));
        log.warn("Token解析成功");
        return userVo;
    }
    
    @Override
    public UserVo status(String token) {
        //解析token
        return getUserByToken(token);
    }
}
