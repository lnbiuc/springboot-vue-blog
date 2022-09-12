package top.beyondhorizon.service;

import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;

import java.util.Map;

/**
 * ClassName: ErrorService
 * date: 2022/9/10 20:56
 *
 * @author ayanamirei
 */


public interface ErrorService {
    
    /**
     * 分页获取错误日志
     *
     * @param params 分页信息
     * @return re
     */
    Map<String, Object> getAll(PageParams params);
    
}
