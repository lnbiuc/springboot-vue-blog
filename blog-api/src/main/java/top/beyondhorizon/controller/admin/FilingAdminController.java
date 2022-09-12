package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Article;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.FilingService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: FilingAdminController
 * date: 2022/6/16 00:19
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("admin/filing")
public class FilingAdminController {
    private final FilingService filingService;
    
    public FilingAdminController(FilingService filingService) {
        this.filingService = filingService;
    }
    
    @LogAnnotation(module = "分类管理", operation = "获取所有分类信息")
    @PostMapping("get")
    public RetMsg getAll(@RequestBody PageParams params) {
        Map<String, Object> map = filingService.getArticleFilingInfo(params);
        return RetMsg.success(map);
    }
    
    @LogAnnotation(module = "分类管理", operation = "将文章移除某个分类")
    @PostMapping("remove")
    public RetMsg remove(@RequestParam String articleId) {
        int delete = filingService.remove(articleId);
        if (delete > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "分类管理", operation = "获取所有没有分类的文章")
    @PostMapping("getUnFiling")
    public RetMsg unFiling() {
        List<Article> unFiling = filingService.getUnFiling();
        return RetMsg.success(unFiling);
    }
    
    @LogAnnotation(module = "分类管理", operation = "重命名分类")
    @PostMapping("reName")
    public RetMsg reName(@RequestParam String filingName, @RequestParam Integer filingId) {
        int i = filingService.reName(filingId, filingName);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "分类管理", operation = "添加一个新的分类")
    @PostMapping("add")
    public RetMsg add(@RequestParam String filingName) {
        return filingService.add(filingName);
    }
    
    @LogAnnotation(module = "分类管理", operation = "删除一个分离")
    @PostMapping("delete")
    public RetMsg delete(@RequestParam String filingId) {
        int i = filingService.deleteFiling(filingId);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "分类管理", operation = "给一篇文章设置分裂")
    @PostMapping("set")
    public RetMsg set(@RequestParam String articleId, @RequestParam String filingName) {
        return filingService.set(articleId, filingName);
    }
}
