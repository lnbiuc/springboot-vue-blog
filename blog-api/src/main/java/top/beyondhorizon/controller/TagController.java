package top.beyondhorizon.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.service.TagService;
import top.beyondhorizon.service.impl.TagServiceImpl;

/**
 * ClassName: TagController
 * date: 2022/5/30 21:28
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("tag")
@Slf4j
public class TagController {
    private final TagService tagService;
    
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }
    
    @LogAnnotation(module = "标签", operation = "获取所有标签")
    @RequestMapping("all")
    public RetMsg tags() {
        return RetMsg.success(tagService.getAllTag());
    }
    
    @LogAnnotation(module = "标签", operation = "添加一个标签")
    @PostMapping("add")
    public RetMsg add(@RequestParam String tagName) {
        if (tagName == null || tagName.equals("")) {
            return RetMsg.fail(ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getCode(),
                    ErrorCode.INCOMPLETE_REQUEST_PARAMETERS.getMsg());
        }
        int i = tagService.addTag(tagName);
        if (i == 2) {
            return RetMsg.fail(ErrorCode.TAG_ALREADY_EXISTS.getCode(),
                    ErrorCode.TAG_ALREADY_EXISTS.getMsg());
        }
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}
