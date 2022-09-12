package top.beyondhorizon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@Slf4j
public class MyBlogApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MyBlogApplication.class, args);
        log.warn("|-----------------------------------------------------------|");
        log.warn("服务器开始运行，{}", new SimpleDateFormat
                ("今天是 " + "yyyy 年 MM 月 dd 日 E HH 点 mm 分 ss 秒").
                format(new Date()));
        log.warn("|-----------------------------------------------------------|");
    }
}
