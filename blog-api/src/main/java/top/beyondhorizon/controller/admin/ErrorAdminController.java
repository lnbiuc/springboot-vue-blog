package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.ErrorService;

import java.util.Map;

/**
 * ClassName: ErrorController
 * date: 2022/9/10 20:50
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("admin/error")
public class ErrorAdminController {
    
    private final ErrorService errorService;
    
    public ErrorAdminController(ErrorService errorService) {
        this.errorService = errorService;
    }
    
    @PostMapping("get")
    @LogAnnotation(module = "错误日志管理", operation = "获取错误日志")
    public RetMsg get(@RequestBody PageParams params) {
        Map<String, Object> all = errorService.getAll(params);
        return RetMsg.success(all);
    }
}
