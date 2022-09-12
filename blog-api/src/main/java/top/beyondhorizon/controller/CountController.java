package top.beyondhorizon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.CountParams;
import top.beyondhorizon.service.CountService;

/**
 * @author ayanamirei
 * @ProjectName: MyBlog
 * @ClassName: CountController
 * @create 2022-06-24 22:46
 */

@RestController
public class CountController {
    private final CountService countService;
    
    public CountController(CountService countService) {
        this.countService = countService;
    }
    
    
    @LogAnnotation(module = "统计", operation = "获取统计信息")
    @RequestMapping("count")
    public RetMsg count() {
        CountParams count = countService.count();
        return RetMsg.success(count);
    }
}
