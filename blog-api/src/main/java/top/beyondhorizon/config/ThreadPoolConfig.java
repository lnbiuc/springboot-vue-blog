package top.beyondhorizon.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * ClassName: ThreadPoolConfig
 * date: 2022/6/3 19:38
 *
 * @author ayanamirei
 */

@Configuration
//开启多线程
@EnableAsync
public class ThreadPoolConfig
{
    @Bean("taskExecutor")
    public Executor asyncServiceExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(5);
        //最大线程数
        executor.setMaxPoolSize(20);
        //队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        //线程活跃时间（秒）
        executor.setKeepAliveSeconds(120);
        //默认线程名
        executor.setThreadNamePrefix("viewCountAsyncExecutor");
        //所有任务结束再关闭线程池 true
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}
