package top.beyondhorizon.controller;


import org.springframework.web.bind.annotation.*;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.service.MessageService;

import java.util.Map;

/**
 * ClassName: MessageController
 * date: 2022/9/7 18:03
 *
 * @author ayanamirei
 */


@RestController
@RequestMapping("message")
public class MessageController {
    
    private final MessageService messageService;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @PostMapping("get")
    @LogAnnotation(module = "留言", operation = "获取留言")
    public RetMsg get(@RequestBody PageParams params) {
        Map<String, Object> messages = messageService.getMessages(params);
        return RetMsg.success(messages);
    }
    
    @PostMapping("send")
    @LogAnnotation(module = "留言", operation = "发送留言")
    public RetMsg createMessage(@RequestParam String qqNumber, @RequestParam String content) {
        if (! qqNumber.matches("[1-9][0-9]{5,11}") || qqNumber.equals("")) {
            return RetMsg.fail(ErrorCode.ILLEGAL.getCode(),
                    ErrorCode.ILLEGAL.getMsg());
        }
        if (content.equals("")) {
            return RetMsg.fail(ErrorCode.ILLEGAL.getCode(),
                    ErrorCode.ILLEGAL.getMsg());
        }
        int message = messageService.createMessage(qqNumber, content);
        if (message == 2) {
            return RetMsg.fail(ErrorCode.QQ_NUMBER_NOT_EXIST.getCode(),
                    ErrorCode.QQ_NUMBER_NOT_EXIST.getMsg());
        }
        if (message > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}
