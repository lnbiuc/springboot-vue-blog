package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.beyondhorizon.entity.Error;
import top.beyondhorizon.mapper.ErrorMapper;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.ErrorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ErrorServiceImpl
 * date: 2022/9/10 20:57
 *
 * @author ayanamirei
 */

@Service
public class ErrorServiceImpl implements ErrorService {
    
    private final ErrorMapper errorMapper;
    
    public ErrorServiceImpl(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }
    
    /**
     * 分页获取错误日志
     *
     * @param params 分页信息
     * @return re
     */
    @Override
    public Map<String, Object> getAll(PageParams params) {
        Page<Error> page = new Page<>(params.getPageNumber(), params.getPageSize());
        LambdaQueryWrapper<Error> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Error::getTimestamp);
        Page<Error> selectPage = errorMapper.selectPage(page, queryWrapper);
        List<Error> records = selectPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("current", selectPage.getCurrent());
        map.put("size", selectPage.getSize());
        map.put("total", selectPage.getTotal());
        return map;
    }
}
