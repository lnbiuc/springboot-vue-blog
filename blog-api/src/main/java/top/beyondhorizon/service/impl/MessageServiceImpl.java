package top.beyondhorizon.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.beyondhorizon.constant.ErrorCode;
import top.beyondhorizon.entity.Message;
import top.beyondhorizon.entity.MessageAuthor;
import top.beyondhorizon.mapper.ArticleMapper;
import top.beyondhorizon.mapper.MessageAuthorMapper;
import top.beyondhorizon.mapper.MessageMapper;
import top.beyondhorizon.model.dto.QqAvatarRespDto;
import top.beyondhorizon.model.dto.RetMsg;
import top.beyondhorizon.model.params.PageParams;
import top.beyondhorizon.model.vo.MessageVo;
import top.beyondhorizon.service.MessageService;
import top.beyondhorizon.utils.HttpContextUtils;
import top.beyondhorizon.utils.IpUtils;
import top.beyondhorizon.utils.QqAvatar;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ClassName: MessageServiceImpl
 * date: 2022/9/7 18:04
 *
 * @author ayanamirei
 */

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    
    private final MessageMapper messageMapper;
    
    private final MessageAuthorMapper messageAuthorMapper;
    
    public MessageServiceImpl(MessageMapper messageMapper, MessageAuthorMapper messageAuthorMapper) {
        this.messageMapper = messageMapper;
        this.messageAuthorMapper = messageAuthorMapper;
    }
    
    /**
     * 分页获取评论
     *
     * @param params 分页参数
     * @return 返回List<MessageVo></>
     */
    @Override
    public Map<String, Object> getMessages(PageParams params) {
        log.info("获取留言");
        Page<Message> page;
        page = new Page<>(params.getPageNumber(), params.getPageSize());
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Message::getCreateTime);
        Page<Message> res = messageMapper.selectPage(page, null);
        List<Message> records = res.getRecords();
        List<MessageVo> messageVos = new ArrayList<>();
        for (Message record : records) {
            MessageVo messageVo = setAuthor(record);
            messageVos.add(messageVo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", messageVos);
        map.put("total", page.getTotal());
        map.put("size", page.getSize());
        map.put("current", page.getCurrent());
        return map;
    }
    
    /**
     * 发表评论
     *
     * @param qqNumber qq号
     * @param content  评论内容
     * @return re
     */
    @Override
    public int createMessage(String qqNumber, String content) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = IpUtils.getIpAddr(request);
        Message message = new Message(null, qqNumber, content, new Date(), ip, null);
        QqAvatar qqAvatar = new QqAvatar();
        QqAvatarRespDto infoByQqNumber = qqAvatar.getInfoByQqNumber(qqNumber);
        if (infoByQqNumber.getAvatar() == null) {
            return 2;
        }
        MessageAuthor messageAuthor = new MessageAuthor(qqNumber, infoByQqNumber.getNickname(), infoByQqNumber.getAvatar());
        LambdaQueryWrapper<MessageAuthor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageAuthor::getQq, qqNumber);
        MessageAuthor author = messageAuthorMapper.selectOne(queryWrapper);
        if (author == null) {
            messageAuthorMapper.insert(messageAuthor);
        }
        return messageMapper.insert(message);
    }
    
    /**
     * 删除留言
     *
     * @param messageId id
     * @return re
     */
    @Override
    public int deleteMessageById(String messageId) {
        LambdaQueryWrapper<Message> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Message::getId, messageId);
        return messageMapper.delete(queryWrapper);
    }
    
    private MessageVo setAuthor(Message message) {
        LambdaQueryWrapper<MessageAuthor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MessageAuthor::getQq, message.getAuthorId());
        MessageAuthor messageAuthor = messageAuthorMapper.selectOne(queryWrapper);
        MessageVo messageVo = new MessageVo();
        messageVo.setId(message.getId());
        messageVo.setNickname(messageAuthor.getNickname());
        messageVo.setAvatar(messageAuthor.getAvatar());
        messageVo.setContent(message.getContent());
        messageVo.setTime(message.getCreateTime());
        messageVo.setIp(message.getIp());
        return messageVo;
    }
}
