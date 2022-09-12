package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.TagName;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.TagService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: TagAdminController
 * date: 2022/6/16 00:18
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("admin/tag")
public class TagAdminController {
    private final TagService tagService;
    
    public TagAdminController(TagService tagService) {
        this.tagService = tagService;
    }
    
    @LogAnnotation(module = "标签管理", operation = "获取所有标签")
    @PostMapping("get")
    public RetMsg get(@RequestBody PageParams params) {
        Map<String, Object> tag = tagService.getTag(params);
        return RetMsg.success(tag);
    }
    
    @LogAnnotation(module = "标签管理", operation = "添加一个标签")
    @PostMapping("add")
    public RetMsg add(@RequestParam String tagName) {
        int i = tagService.addTag(tagName);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "标签管理", operation = "删除一个标签")
    @PostMapping("delete")
    public RetMsg delete(@RequestParam String tagId) {
        int delete = tagService.delete(tagId);
        if (delete > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "标签管理", operation = "修改标签名")
    @PostMapping("edit")
    public RetMsg edit(@RequestParam String tagId, @RequestParam String tagName) {
        int i = tagService.edit(tagId, tagName);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
    
    @LogAnnotation(module = "标签管理", operation = "获取一篇文章的所有标签")
    @PostMapping("getTagByAId")
    public RetMsg getTagByArticleId(@RequestParam String articleId) {
        List<TagName> tagByArticleId = tagService.getTagByArticleId(articleId);
        return RetMsg.success(tagByArticleId);
    }
}
