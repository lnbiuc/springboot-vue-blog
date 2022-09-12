package top.beyondhorizon.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import top.beyondhorizon.entity.User;
import top.beyondhorizon.model.dto.UserDto;
import top.beyondhorizon.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * ClassName: UserRealm
 * date: 2022/6/1 00:47
 *
 * @author ayanamirei
 */

@Slf4j
public class UserRealm extends AuthorizingRealm
{
    @Autowired
    private UserService userService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserDto userDto = userService.getUserByUsername(token.getUsername());
        if (userDto == null)
        {
            return null;
        }
        String username = token.getUsername();
        String password = userDto.getPassword();
        String databaseSalt = userService.getSaltByUsername(username);
        ByteSource salt = ByteSource.Util.bytes(databaseSalt);
        String realmName = this.getName();
        return new SimpleAuthenticationInfo(userDto, password, salt, realmName);
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> permission = new HashSet<>();
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        permission.add(String.valueOf(currentUser.getPermission()));
        log.warn("currentUser.getPermission() === {}", currentUser.getPermission());
        //设置权限 默认普通用户为false
        info.setStringPermissions(permission);
        return info;
    }
}
