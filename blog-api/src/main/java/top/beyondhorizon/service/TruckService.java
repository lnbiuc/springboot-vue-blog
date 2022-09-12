package top.beyondhorizon.service;

import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;

import java.util.Map;

/**
 * ClassName: TruckService
 * date: 2022/9/10 21:07
 *
 * @author ayanamirei
 */


public interface TruckService {
    
    /**
     * 分页获取访问日志
     *
     * @param params 分页
     * @return re
     */
    Map<String, Object> getAll(PageParams params);
    
    
    int searchIpAndDelete(String ip);
}
