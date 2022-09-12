package top.beyondhorizon.config;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ClassName: ShiroConfig
 * date: 2022/6/1 00:39
 *
 * @author ayanamirei
 */

@Configuration
public class ShiroConfig
{
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Qualifier("securityManager")
            DefaultWebSecurityManager defaultWebSecurityManager
    )
    {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        
        /*
         anon：所有人可访问
         authc：认证才能访问
         user：必须有记住我功能才能访问
         perms：拥有对某个资源的权限才能访问
         roles：拥有某个角色权限才能访问
         */
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("/user/logout", "authc");
//        map.put("/comments/add", "authc");
//        map.put("/articles/publish", "authc");
//        map.put("/user/status", "authc");
//        map.put("/admin/**", "authc");
//        bean.setFilterChainDefinitionMap(map);

//        Map<String, Filter> filters = bean.getFilters();
//        filters.put("authc", new StatelessAuthcFilter());
//        bean.setFilters(filters);
        bean.setLoginUrl("/login");

        bean.setUnauthorizedUrl("/noPermission");
        return bean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            @Qualifier("userRealm")
            UserRealm userRealm
    )
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm(HashedCredentialsMatcher matcher)
    {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher()
    {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }
}
