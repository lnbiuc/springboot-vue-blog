package top.beyondhorizon.config;


import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: MybatisConfig
 * date: 2022/5/30 20:10
 *
 * @author ayanamirei
 */

@Configuration
@MapperScan("top.beyondhorizon.mapper")
public class MybatisConfig
{
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor()
    {
        //创建拦截器
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
