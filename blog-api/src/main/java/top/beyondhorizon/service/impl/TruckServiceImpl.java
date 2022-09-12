package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Truck;
import top.beyondhorizon.mapper.TruckMapper;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.TruckService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: TruckServiceImpl
 * date: 2022/9/10 21:07
 *
 * @author ayanamirei
 */

@Service
public class TruckServiceImpl implements TruckService {
    
    private final TruckMapper truckMapper;
    
    public TruckServiceImpl(TruckMapper truckMapper) {
        this.truckMapper = truckMapper;
    }
    
    /**
     * 分页获取访问日志
     *
     * @param params 分页
     * @return re
     */
    @Override
    public Map<String, Object> getAll(PageParams params) {
        Page<Truck> page = new Page<>(params.getPageNumber(), params.getPageSize());
        LambdaQueryWrapper<Truck> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Truck::getTime);
        Page<Truck> truckPage = truckMapper.selectPage(page, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("data", truckPage.getRecords());
        map.put("current", truckPage.getCurrent());
        map.put("total", truckPage.getTotal());
        map.put("size", truckPage.getSize());
        return map;
    }
    
    @Override
    public int searchIpAndDelete(String ip) {
        LambdaQueryWrapper<Truck> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Truck::getIp, ip);
        return truckMapper.delete(queryWrapper);
    }
}
