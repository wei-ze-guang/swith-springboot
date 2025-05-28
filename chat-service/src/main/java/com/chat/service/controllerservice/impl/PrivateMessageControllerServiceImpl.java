package com.chat.service.controllerservice.impl;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.PrivateMessageDto;
import com.chat.common.mapstructmappr.PrivateMessageMapperStr;
import com.chat.common.model.PrivateMessage;
import com.chat.common.utils.Result;
import com.chat.common.utils.SnowflakeIdWorker;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.controllerservice.PrivateMessageControllerService;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.repository.mapper.PrivateMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class PrivateMessageControllerServiceImpl implements PrivateMessageControllerService {

    @Autowired
    PrivateMessageMapper privateMessageMapper;

    @Autowired
    private PrivateMessageMapperStr privateMessageMapperStr;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RabbitMQSendPrimaryMessageService rabbitMQSendPrimaryMessageService;
    /**
     * 查找两个好友之间的全部私信信息
     * 1 查找数据库
     * 2 返回结果
     *
     * @param userId
     * @param toUserId
     * @return
     */
    @Override
    public Result findTwoFriendPrivateMessages(String userId, String toUserId) {
        List<PrivateMessage> allActiveMessages = privateMessageMapper.selectPrivateMessageByUserIdAndToUserId(userId, toUserId);
        return allActiveMessages != null ? Result.OK(privateMessageMapperStr.toPrivateMessageVoList(allActiveMessages)) : Result.FAIL(Collections.emptyList());
    }

    /**
     * 增加一条好友信息
     * 1 插入数据库
     * 2 发送到rabbitmq通知被发送方
     *
     * @param privateMessageDto
     * @return
     */
    @Override
    public Result addPrivateMessage(PrivateMessageDto privateMessageDto) {
        log.info(privateMessageDto.toString());
        PrivateMessage privateMessage = privateMessageMapperStr.toPrivateMessage(privateMessageDto);
        long id = snowflakeIdWorker.nextId();
        privateMessage.setId(id);
        log.info(privateMessage.toString());
        int i = privateMessageMapper.insertPrivateMessage(privateMessage);
        if(i > 0 ){
            privateMessageDto.setId(id);
            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setMessageFrom(privateMessageDto.getUserId());
            webSocketVo.setMessageTo(privateMessageDto.getToUserId());
            webSocketVo.setMessageType(WebSocketMessageType.PRIVATE_MESSAGE);
            webSocketVo.setType(WebSocketVo.privateType);
            webSocketVo.setMessageBody(privateMessageDto);
            rabbitMQSendPrimaryMessageService.sendPrivateMessage(webSocketVo);
        }
        return i > 0 ? Result.OK(privateMessage) : Result.FAIL(false);
    }
}
