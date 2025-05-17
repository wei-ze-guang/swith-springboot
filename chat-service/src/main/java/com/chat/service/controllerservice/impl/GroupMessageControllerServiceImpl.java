package com.chat.service.controllerservice.impl;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.GroupMessageDto;
import com.chat.common.mapstructmappr.GroupMessageMapperStr;
import com.chat.common.model.GroupMember;
import com.chat.common.model.GroupMessage;
import com.chat.common.utils.Result;
import com.chat.common.utils.SnowflakeIdWorker;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.controllerservice.GroupMessageControllerService;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.repository.mapper.GroupMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class GroupMessageControllerServiceImpl implements GroupMessageControllerService {

    @Autowired
    GroupMessageMapper groupMessageMapper;

    @Autowired
    GroupMessageMapperStr groupMessageMapperStr;

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private RabbitMQSendPrimaryMessageService rabbitMQSendPrimaryMessageService;
    /**
     * 用户发送群信息
     * 1 插入数据库
     * 2发送消息到rabbitmq，通知其他人
     *
     * @param groupMessageDto
     * @return
     */
    @Override
    public Result addGroupMessage(GroupMessageDto groupMessageDto) {
        GroupMessage groupMessage = groupMessageMapperStr.toGroupMessage(groupMessageDto);
        Long id = snowflakeIdWorker.nextId();
        groupMessage.setId(id);
        int i = groupMessageMapper.insertGroupMessage(groupMessage);
        if (i > 0){
            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setMessageTo(String.valueOf(groupMessage.getGroupId()));
            webSocketVo.setMessageType(WebSocketMessageType.GROUP_MESSAGE);
            webSocketVo.setMessageFrom(groupMessage.getUserId());
            webSocketVo.setMessageBody(groupMessage);
            webSocketVo.setType(WebSocketVo.publicType);
            rabbitMQSendPrimaryMessageService.sendGroupMessage(webSocketVo);
            return Result.OK(groupMessage);
        }
        return  Result.FAIL(false);
    }

    /**
     * 根据groupId  获取一个群的所有聊天信息
     * 1 查询数据库返回
     *
     * @param groupId
     * @return
     */
    @Override
    public Result findGroupMessageByGroupId(Integer groupId) {
        List<GroupMessage> groupMessages = groupMessageMapper.getGroupMessageByGroupId(groupId);
        return groupMessages != null ?  Result.OK(groupMessages) : Result.FAIL(Collections.emptyList());
    }
}
