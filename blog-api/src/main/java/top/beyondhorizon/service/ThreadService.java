package top.beyondhorizon.service;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.entity.Error;
import top.beyondhorizon.entity.Truck;
import top.beyondhorizon.mapper.ArticleMapper;
import top.beyondhorizon.mapper.ErrorMapper;
import top.beyondhorizon.mapper.TruckMapper;
import top.beyondhorizon.model.params.PublishParams;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * ClassName: ThreadService
 * date: 2022/6/3 19:35
 *
 * @author ayanamirei
 */

@Component
@Slf4j
public class ThreadService {
    
    private final ArticleMapper articleMapper;
    
    public ThreadService(ArticleMapper articleMapper, TruckMapper truckMapper, ErrorMapper errorMapper) {
        this.articleMapper = articleMapper;
        this.truckMapper = truckMapper;
        this.errorMapper = errorMapper;
    }
    
    private final TruckMapper truckMapper;
    
    private final ErrorMapper errorMapper;
    
    @Async
    public void updateViewCount(LambdaUpdateWrapper<Article> updateWrapper, Article updataArticle) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int i = articleMapper.update(updataArticle, updateWrapper);
        if (i > 0) {
            log.warn("阅读数增加end");
        }
    }
    
    @Async
    public void visitorInfo(Map<String, String> map, HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        String brName = userAgent.getBrowser().getName();
        String osName = userAgent.getOperatingSystem().getName();
        if (brName.equals("Unknown") || osName.equals("Unknown")) {
            log.info("Unknown 未记录，IP未：{}", map.get("ip"));
            return;
        }
        Truck truck = new Truck();
        truck.setId(null);
        truck.setModule(map.get("module"));
        truck.setOperation(map.get("operation"));
        truck.setMethod(map.get("method"));
        truck.setIp(map.get("ip"));
        truck.setExecuteTime(map.get("executeTime"));
        truck.setBrName(brName);
        truck.setOsName(osName);
        truck.setTime(new Date());
        int insert = truckMapper.insert(truck);
        if (insert > 0) {
            log.info("访客信息已经记录");
        } else {
            log.error("访客信息记录错误");
        }
    }
    
    @Async
    public void recordError(Map<String, String> map) {
        Error error = new Error();
        error.setEMsg(map.get("eMsg"));
        error.setStackTrace(map.get("fillInStackTrace"));
        error.setTimestamp(new Date());
        int insert = errorMapper.insert(error);
        if (insert > 0) {
            log.info("错误日志已记录");
        } else {
            log.error("错误日志记录失败");
        }
    }
}
