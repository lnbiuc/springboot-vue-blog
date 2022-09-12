package top.beyondhorizon.scheduled;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: ScheduledTash
 * date: 2022/9/4 01:39
 *
 * @author ayanamirei
 */

@Component
@Slf4j
public class ScheduledTask {
    @Scheduled(cron = "0/10 * *  * * ? ")
    public void executeTask() {
        Date date = new Date();
        log.warn("定时任务执行" + date);
    }
}
