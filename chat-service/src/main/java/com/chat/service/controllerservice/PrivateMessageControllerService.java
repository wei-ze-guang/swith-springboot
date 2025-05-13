package com.chat.service.controllerservice;

import com.chat.common.dto.PrivateMessageDto;
import com.chat.common.model.PrivateMessage;

import javax.xml.transform.Result;

public interface PrivateMessageControllerService {

    /**
     * 查找两个好友之间的全部私信信息
     * 1 查找数据库
     * 2 返回结果
     * @param toUserId
     * @param toUserId
     * @return
     */
    public Result findTwoFriendPrivateMessages(String UserId,String toUserId);

    /**
     * 增加一条好友信息
     * 1 插入数据库
     * 2 发送到rabbitmq通知被发送方
     * @param privateMessageDto
     * @return
     */
    public Result addPrivateMessage(PrivateMessageDto privateMessageDto);
}
