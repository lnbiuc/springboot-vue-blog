package top.beyondhorizon.service;

import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;

import java.util.Map;

/**
 * ClassName: MessageService
 * date: 2022/9/7 18:04
 *
 * @author ayanamirei
 */


public interface MessageService {
    
    /**
     * 分页获取评论
     *
     * @param params 分页参数
     * @return 返回List<MessageVo></>
     */
    Map<String, Object> getMessages(PageParams params);
    
    /**
     * 发表评论
     *
     * @param qqNumber qq号
     * @param content  评论内容
     * @return re
     */
    int createMessage(String qqNumber, String content);
    
    /**
     * 删除留言
     *
     * @param messageId id
     * @return re
     */
    int deleteMessageById(String messageId);
}
