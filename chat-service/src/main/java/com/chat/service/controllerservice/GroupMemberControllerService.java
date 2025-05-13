package com.chat.service.controllerservice;

import com.chat.common.dto.GroupMemberDto;
import com.chat.common.utils.Result;


public interface GroupMemberControllerService {

    /**
     * 用户被提出群聊或者主动退出群聊
     * 1 修改数据空
     * 2 删除群关系表，聊天记录表
     * 2发送信息到rabbitmq 通知其他人
     * @param userId
     * @param groupId
     * @return
     */
    public Result deleteGroupMember(String userId, Integer groupId) ;

    /**
     * 用户添加群
     * 插入数据库
     * 发送到rabbitmq通知其他人
     * @param groupMemberDto
     * @return
     */
    public Result addGroupMember(GroupMemberDto groupMemberDto) ;

    /**
     * 获取用户左右的群信息
     * 1  查询数据库返回信息s
     * @param userId  用户userId
     * @return
     */
    public Result findGroupsByUserId(String userId) ;
}
