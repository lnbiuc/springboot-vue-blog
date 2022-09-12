package top.beyondhorizon.aop.log;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.beyondhorizon.service.ThreadService;
import top.beyondhorizon.utils.HttpContextUtils;
import top.beyondhorizon.utils.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: LogAspect
 * date: 2022/6/4 02:57
 *
 * @author ayanamirei
 */

@Component
@Slf4j
@Aspect
public class LogAspect {
    private final ThreadService threadService;
    
    public LogAspect(ThreadService threadService) {
        this.threadService = threadService;
    }
    
    @Pointcut("@annotation(top.beyondhorizon.aop.log.LogAnnotation)")
    public void pt() {
    }
    
    //环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time);
        return result;
        
    }
    
    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        String module = logAnnotation.module();
        log.info("module:{}", module);
        String operation = logAnnotation.operation();
        log.info("operation:{}", operation);
        
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");
//        //请求的参数
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args.length < 255) {
            String params = JSON.toJSONString(args[0]);
            log.info("params:{}", params);
        }
        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ipAddr = IpUtils.getIpAddr(request);
        log.info("ip:{}", ipAddr);
        log.info("execute time : {} ms", time);
        log.info("=====================log end================================");
        Map<String, String> map = new HashMap<>();
        map.put("module", module);
        map.put("operation", operation);
        map.put("method", className + "." + methodName);
        map.put("ip", ipAddr);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("S");
        map.put("executeTime", simpleDateFormat.format(time));
        threadService.visitorInfo(map, request);
    }
}
