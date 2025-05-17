package com.chat.service.controllerservice.impl;

import com.chat.common.constant.DbConstant;
import com.chat.common.constant.ModuleConstant;
import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.GroupInfoDto;
import com.chat.common.exception.DbHandlerException;
import com.chat.common.exception.ErrorCodeEnum;
import com.chat.common.mapstructmappr.GroupInfoMapperStr;
import com.chat.common.mapstructmappr.GroupInfoMapperStr;
import com.chat.common.model.GroupInfo;
import com.chat.common.model.GroupMember;
import com.chat.common.vo.GroupInfoVo;
import com.chat.common.utils.Result;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.controllerservice.GroupInfoControllerService;
import com.chat.service.event.RabbitMqEvent;
import com.repository.mapper.GroupInfoMapper;
import com.repository.mapper.GroupMemberMapper;
import com.repository.mapper.GroupMessageMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * @author 86199
 */
@Service
@Slf4j
public class GroupInfoControllerServiceImpl implements GroupInfoControllerService {

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Autowired
    private GroupMemberMapper groupMemberMapper;

    @Autowired
    private GroupMessageMapper groupMessageMapper;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ApplicationEventPublisher publisher;


    @Autowired
    private GroupInfoMapperStr groupInfoMapperStr;
    /**
     * 根据groupIP获取这个群的信息
     * 1 查询数据库返回结果
     *
     * @param groupId
     * @return Result
     */
    @Override
    public Result findGroupInfoByGroupId(Integer groupId) {
            GroupInfo groupInfo =  groupInfoMapper.selectByGroupId(groupId);
            return groupInfo != null ? Result.OK(groupInfoMapperStr.toGroupInfoVo(groupInfo)) : Result.FAIL("找不到群信息");
    }

    /**
     * 根据关键词查群群信息
     * 1 查询数据库返回结果
     *
     * @param keyword
     * @return
     */
    @Override
//    @ServiceLayer(value = "通过关键字找群",module = ModuleConstant.MODULE_FUZZY_QUERY_GROUP_INFO_BY_WORD)
    public Result findGroupsByKeyword(String keyword) {
            List<GroupInfo> groupInfos = groupInfoMapper.searchByGroupName(keyword);
            return Result.OK(Objects.requireNonNullElse(groupInfoMapperStr.toGroupInfoVoList(groupInfos), Collections.emptyList()));
    }

    /**
     * 创建群聊
     * 1 插入数据库
     * 2 插入groupmember数据库
     * 这里需要使用事务
     *
     * @param groupInfoDto
     * @return
     */
    @Override
    public Result addGroupInfo(GroupInfoDto groupInfoDto) {
        // 定义事务属性
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // 获取事务状态
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // 1. 插入群信息
            int i = groupInfoMapper.insertGroupInfo(groupInfoMapperStr.toGroupInfo(groupInfoDto));

            // 2. 插入群成员信息
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(groupInfoDto.getGroupId());
            groupMember.setUserId(groupInfoDto.getUserId());
            // 群主
            groupMember.setIdentity(DbConstant.DB_GROUP_MEMBER_IDENTITY_OWNER);
            groupMember.setIsDeleted(DbConstant.DB_IS_DELETE_DEFAULT);
            groupMemberMapper.insertGroupMember(groupMember);

            // 提交事务
            transactionManager.commit(status);
            return Result.OK(true);

        } catch (Exception e) {
            // 回滚事务
            transactionManager.rollback(status);
            return Result.FAIL("添加失败");
        }
    }


    /**
     * 解散群聊
     * 1 逻辑上删除这个群信息
     *
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public Result updateGroupInfo(Integer groupId) {
        try{
            // 删除这个群
            int i = groupInfoMapper.logicalDeleteByGroupId(groupId);
            //删除这个群的所有信息
            groupMemberMapper.softDeleteOneGroupAllMember(groupId);
            //删除这个群的所有群聊天信息
            groupMessageMapper.softDeleteGroupMessageByGroupId(groupId);
            if(i> 0){
                WebSocketVo webSocketVo = new WebSocketVo();
                webSocketVo.setMessageType(WebSocketMessageType.GROUP_DESTROY);
                webSocketVo.setType(WebSocketVo.publicType);

                webSocketVo.setMessageTo(String.valueOf(groupId));
                //TODO 这里还需要处理,这里会删除所有成员，发送信息的时候查询不到群好友
                publisher.publishEvent(new RabbitMqEvent(this,webSocketVo));
                return Result.OK(groupId);
            }
            return Result.FAIL("该群不存在或者已经被删除");
        }catch (RuntimeException e){
            throw new DbHandlerException(ErrorCodeEnum.DB_ERROR);
        }
    }
}
