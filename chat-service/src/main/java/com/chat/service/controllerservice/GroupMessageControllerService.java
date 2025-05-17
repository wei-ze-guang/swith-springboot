package com.chat.service.controllerservice;

import com.chat.common.dto.GroupMessageDto;
import com.chat.common.model.GroupMessage;
import com.chat.common.utils.Result;


public interface GroupMessageControllerService {
    /**
     *  用户发送群信息
     *  1 插入数据库
     *  2发送消息到rabbitmq，通知其他人
     * @param groupMessageDto
     * @return
     */
    public Result addGroupMessage(GroupMessageDto groupMessageDto) ;


    /**
     * 根据groupId  获取一个群的所有聊天信息
     * 1 查询数据库返回
     * @param groupId
     * @return
     */
    public Result findGroupMessageByGroupId(Integer groupId);
}
