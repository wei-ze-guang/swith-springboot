package com.chat.service.controllerservice;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.GroupMemberDto;
import com.chat.common.utils.Result;
import me.doudan.doc.annotation.ServiceLayer;


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
     * 获取用户所有的群信息
     * 1  查询数据库返回信息s
     * @param userId  用户userId
     * @return
     */
    public Result findGroupsByUserId(String userId) ;


    /**
     * 获取群的所有群成员
     */
    @ServiceLayer(value = "获取一个群的所有群成员信息",module = ModuleConstant.MODULE_THE_GROUP_ALL_MEMBERS_INFO)
    public Result findGroupAllMembersByGroupId(Integer groupId) ;
}
