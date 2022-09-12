package top.beyondhorizon.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.service.ThreadService;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AllExceptionHandler
 * date: 2022/5/31 17:21
 *
 * @author ayanamirei
 */

@RestControllerAdvice
@Slf4j
public class AllExceptionHandler {
    
    private final ThreadService threadService;
    
    public AllExceptionHandler(ThreadService threadService) {
        this.threadService = threadService;
    }
    
    @ExceptionHandler(Exception.class)
    public RetMsg doException(Exception e) {
        e.printStackTrace();
        String fillInStackTrace = e.fillInStackTrace().toString();
        String eMsg = e.getMessage();
        log.error("error-msg-{}", eMsg);
        Map<String, String> map = new HashMap<>();
        map.put("eMsg", eMsg);
        map.put("fillInStackTrace", fillInStackTrace);
        threadService.recordError(map);
        return RetMsg.error("sys error");
    }
}