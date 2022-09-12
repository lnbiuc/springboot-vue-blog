package top.beyondhorizon.controller.admin;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.beyondhorizon.aop.log.LogAnnotation;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.service.MessageService;

/**
 * ClassName: MessageController
 * date: 2022/9/10 20:50
 *
 * @author ayanamirei
 */

@RestController
@RequestMapping("admin/message")
public class MessageAdminController {
    
    private final MessageService messageService;
    
    public MessageAdminController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    @PostMapping("delete")
    @LogAnnotation(module = "留言管理", operation = "删除一条留言")
    public RetMsg deleteMessage(@RequestParam String messageId) {
        int i = messageService.deleteMessageById(messageId);
        if (i > 0) {
            return RetMsg.success(null);
        }
        return RetMsg.fail(ErrorCode.UPDATE_FILE.getCode(),
                ErrorCode.UPDATE_FILE.getMsg());
    }
}
