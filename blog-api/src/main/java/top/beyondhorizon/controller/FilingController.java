package top.beyondhorizon.controller;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.FilingName;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.FilingPageParams;
import top.beyondhorizon.model.params.PageInfoParams;
import top.beyondhorizon.model.vo.FilingArticleVo;
import top.beyondhorizon.service.FilingService;

import java.util.List;

/**
 * ClassName: FilingController
 * date: 2022/6/10 00:40
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("filing")
public class FilingController {
    private final FilingService filingService;
    
    public FilingController(FilingService filingService) {
        this.filingService = filingService;
    }
    
    @LogAnnotation(module = "分类", operation = "删除一个分类")
    @PostMapping("deleteTag")
    public RetMsg deleteFilingTag(@RequestParam Integer filingId) {
        int i = filingService.deleteTag(filingId);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "分类", operation = "获取所有分类目录")
    @PostMapping("all")
    public RetMsg allFiling() {
        List<FilingName> filingNames = filingService.allFiling();
        return RetMsg.success(filingNames);
    }
    
    @LogAnnotation(module = "分类", operation = "获取所有文件夹名以及文件夹下的文件")
    @RequestMapping("filing")
    public RetMsg filing() {
        List<FilingArticleVo> filing = filingService.filing();
        return RetMsg.success(filing);
    }
    
    @LogAnnotation(module = "分类", operation = "获取某个分类下的所有文章")
    @PostMapping("get")
    public RetMsg get(@RequestBody FilingPageParams params) {
        PageInfoParams pageInfoParams = filingService.get(params);
        return RetMsg.success(pageInfoParams);
    }
    
    @LogAnnotation(module = "分类", operation = "根据id获取分类名")
    @PostMapping("getFilingName")
    public RetMsg getFilingName(@RequestParam Integer filingId) {
        FilingName filingNameByFilingId = filingService.getFilingNameByFilingId(filingId);
        return RetMsg.success(filingNameByFilingId);
    }
}
