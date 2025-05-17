package com.chat.service.controllerservice;

import com.chat.common.dto.UserRelationDto;
import com.chat.common.model.UserRelation;
import com.chat.common.utils.Result;


public interface UserRelationControllerService {
    /**
     * 增加一条userRelation 用户添加好友
     * 1 插入数据库
     * 2 发送信息到rabbitmq，告知被添加人
     * @param userRelationDto
     * @return
     */
    public Result addUserRelation(UserRelationDto userRelationDto);

    /**
     * 逻辑上删除一条好友关系
     * 1修改数据空
     * 2 发送信息到rabbirmq，告知被删除人
     * @param userRelationDto
     * @return
     */
    public Result deleteUserRelation(UserRelationDto userRelationDto);

    /**
     * 获取用户的所有好友信息
     * @param userId
     */

    public Result findUserAllRelationAndInfo(String userId);
}
