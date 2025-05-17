package com.chat.service.controllerservice.impl;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.GroupMemberDto;
import com.chat.common.mapstructmappr.GroupInfoMapperStr;
import com.chat.common.mapstructmappr.GroupMemberMapperStr;
import com.chat.common.mapstructmappr.UserMapperStr;
import com.chat.common.model.GroupInfo;
import com.chat.common.model.GroupMember;
import com.chat.common.model.User;
import com.chat.common.utils.Result;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.controllerservice.GroupMemberControllerService;
import com.chat.service.event.RabbitMqEvent;
import com.repository.mapper.GroupMemberMapper;
import com.repository.mapper.GroupMessageMapper;
import me.doudan.doc.annotation.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service
public class GroupMemberControllerServiceImpl implements GroupMemberControllerService {
    @Autowired
    private GroupMemberMapper groupMemberMapper;

    @Autowired
    private UserMapperStr userMapperStr;

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Autowired
    private GroupInfoMapperStr groupInfoMapperStr;

    @Autowired
    private GroupMemberMapperStr groupMemberMapperStr;

    @Autowired
    private ApplicationEventPublisher publisher;
    /**
     * 用户被提出群聊或者主动退出群聊
     * 1 修改数据空
     * 2 删除群关系表，聊天记录表
     * 2发送信息到rabbitmq 通知其他人
     *
     * @param userId
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public Result deleteGroupMember(String userId, Integer groupId) {
        /**删除群关系表*/
        int i = groupMemberMapper.updateGroupMemberIsDeleted(groupId, userId);
        /**删除用户的群聊天记录*/
        groupMessageMapper.softDeleteGroupMessageByUserId(userId);
        if(i > 0){
            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setMessageType(WebSocketMessageType.GROUP_MEMBER_EXIT);
            webSocketVo.setMessageFrom(userId);
            webSocketVo.setMessageBody(groupId);
            webSocketVo.setType(WebSocketVo.publicType);
            webSocketVo.setMessageTo(String.valueOf(groupId));
            publisher.publishEvent(new RabbitMqEvent(this, webSocketVo));
            return Result.OK(true);
        }
        return Result.FAIL(false);
    }

    /**
     * 用户添加群
     * 插入数据库
     * 发送到rabbitmq通知其他人
     *
     * @param groupMemberDto
     * @return
     */
    @Override
    @Transactional
    public Result addGroupMember(GroupMemberDto groupMemberDto) {
        Integer isDeleted = groupMemberMapper.selectGroupMemberRelation(groupMemberMapperStr.toGroupMember(groupMemberDto));
        if(isDeleted != null) {
            if(isDeleted == 1){
                int i = groupMemberMapper.updateGroupMember(groupMemberMapperStr.toGroupMember(groupMemberDto));
                if(i > 0){
                    return Result.OK(true);
                }
            }
            // FIXME 这里先不管
            int i = groupMemberMapper.updateGroupMember(groupMemberMapperStr.toGroupMember(groupMemberDto));
            if(i > 0){
                return Result.OK(true);
            }
            return Result.FAIL(false);
        }

        int result = groupMemberMapper.insertGroupMember(groupMemberMapperStr.toGroupMember(groupMemberDto));
        if(result > 0){
            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setType(WebSocketVo.publicType);
            webSocketVo.setMessageBody(groupMemberDto.getGroupId());
            webSocketVo.setMessageTo(String.valueOf(groupMemberDto.getGroupId()));
            webSocketVo.setMessageFrom(groupMemberDto.getUserId());
            webSocketVo.setMessageType(WebSocketMessageType.GROUP_NEW_MEMBER);
            publisher.publishEvent(new RabbitMqEvent(this, webSocketVo));
            return Result.OK(true);
        }
        return   Result.FAIL(false);
    }

    /**
     * 获取用户所有的群信息
     * 1  查询数据库返回信息
     *
     * @param userId 用户userId
     * @return
     */
    @Override
    public Result findGroupsByUserId(String userId) {
        List<GroupInfo> groupMembers = groupMemberMapper.getGroupMembers(userId);
        return groupMembers != null ? Result.OK(groupInfoMapperStr.toGroupInfoVoList(groupMembers)) : Result.FAIL(Collections.emptyList());
    }

    /**
     * 获取群的所有群成员
     *
     * @param groupId
     */
    @Override

    public Result findGroupAllMembersByGroupId(Integer groupId) {
        List<User> list = groupMemberMapper.selectGroupAllMembersByGroupId(groupId);
        return  list != null ? Result.OK(userMapperStr.toVOList(list)) : Result.FAIL(Collections.emptyList());
    }
}
