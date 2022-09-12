package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.TruckService;

import java.util.Map;

/**
 * ClassName: TruckController
 * date: 2022/9/10 20:50
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("admin/truck")
public class TruckAdminController {
    
    private final TruckService truckService;
    
    public TruckAdminController(TruckService truckService) {
        this.truckService = truckService;
    }
    
    @PostMapping("get")
    @LogAnnotation(module = "访客管理", operation = "获取访客列表")
    public RetMsg get(@RequestBody PageParams params) {
        Map<String, Object> all = truckService.getAll(params);
        return RetMsg.success(all);
    }
    
    @PostMapping("delete")
    @LogAnnotation(module = "访客管理", operation = "清除指定ip日志")
    public RetMsg search(@RequestParam String ip) {
        if (ip.equals("") || ! ip.matches("^((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}$")) {
            return RetMsg.fail(ErrorCode.ILLEGAL.getCode(),
                    ErrorCode.ILLEGAL.getMsg());
        }
        int i = truckService.searchIpAndDelete(ip);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}
