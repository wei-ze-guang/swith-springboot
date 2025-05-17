package com.chat.service.controllerservice.impl;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.UserRelationDto;
import com.chat.common.exception.DbHandlerException;
import com.chat.common.exception.ErrorCodeEnum;
import com.chat.common.mapstructmappr.UserMapperStr;
import com.chat.common.mapstructmappr.UserRelationMapperStr;
import com.chat.common.model.User;
import com.chat.common.utils.Result;
import com.chat.common.vo.FriendVO;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.controllerservice.UserRelationControllerService;
import com.chat.service.event.RabbitMqEvent;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.repository.mapper.PrivateMessageMapper;
import com.repository.mapper.UserMapper;
import com.repository.mapper.UserRelationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class UserRelationControllerServiceImpl implements UserRelationControllerService {
    @Autowired
    private UserRelationMapper userRelationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrivateMessageMapper privateMessageMapper;

    @Autowired
    private UserMapperStr userMapperStr;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserRelationMapperStr userRelationMapperStr;
    /**
     * 增加一条userRelation 用户添加好友
     * 1 插入数据库
     * 2 发送信息到rabbitmq，告知被添加人
     *
     * @param userRelationDto
     * @return
     */
    @Override
    @Transactional
    public Result addUserRelation(UserRelationDto userRelationDto) {
        //先检查是否是好友
        List<Integer> list = userRelationMapper.selectUserRelationByUserIdAndFriendUserId(userRelationMapperStr.toUserRelation(userRelationDto));
        log.info("双号的好友关系是:{}", list);

        //TODO 这里先测试之后要修改为list != null && list.size() > 1 && (list.get(0) == list.get(1))
        if(list != null && list.size() > 0 ) {
            if(list.get(0) == 0){
                //这里已经是好友了
            }else {
                //这里是增加添加过
                int i = userRelationMapper.updateUserRelation(userRelationMapperStr.toUserRelation(userRelationDto));
                if(i >1 ){
                    WebSocketVo webSocketVo = new WebSocketVo();
                    webSocketVo.setMessageTo(userRelationDto.getFriendUserId());
                    webSocketVo.setMessageFrom(userRelationDto.getUserId());
                    webSocketVo.setMessageType(WebSocketMessageType.FRIEND_NEW_FRIEND);
                    webSocketVo.setType(WebSocketVo.privateType);
                    publisher.publishEvent(new RabbitMqEvent(this, webSocketVo));
                    return Result.OK(true);
                }
            }
        }
        UserRelationDto userRelationDto1 = new UserRelationDto();
        userRelationDto1.setUserId(userRelationDto.getFriendUserId());
        userRelationDto1.setFriendUserId(userRelationDto.getUserId());
        int i = userRelationMapper.insertUserRelation(userRelationMapperStr.toUserRelation(userRelationDto));
        int i1 = userRelationMapper.insertUserRelation(userRelationMapperStr.toUserRelation(userRelationDto1));
        if(i > 0 && i1 > 0 ){
                WebSocketVo webSocketVo = new WebSocketVo();
                webSocketVo.setMessageTo(userRelationDto.getFriendUserId());
                webSocketVo.setMessageFrom(userRelationDto.getUserId());
                webSocketVo.setMessageType(WebSocketMessageType.FRIEND_NEW_FRIEND);
                webSocketVo.setType(WebSocketVo.privateType);
                publisher.publishEvent(new RabbitMqEvent(this, webSocketVo));
                return Result.OK(true);
        }
        return  Result.FAIL(false);
    }

    /**
     * 逻辑上删除一条好友关系
     * 1修改数据空
     * 2删除聊天记录
     * 3发送信息到rabbitmq，告知被删除人
     *
     * @param userRelationDto
     * @return
     */
    @Override
    @Transactional
    public Result deleteUserRelation(UserRelationDto userRelationDto) {
            int i = userRelationMapper.softDeleteUserRelation(userRelationMapperStr.toUserRelation(userRelationDto));
            privateMessageMapper.softDeleteAllPrivateMessageBothSides(userRelationDto.getUserId(),userRelationDto.getFriendUserId());
            if(i > 0){
                WebSocketVo webSocketVo = new WebSocketVo();
                webSocketVo.setMessageFrom(userRelationDto.getUserId());
                webSocketVo.setMessageTo(userRelationDto.getFriendUserId());
                webSocketVo.setMessageType(WebSocketMessageType.FRIEND_DELETE);
                webSocketVo.setType(WebSocketVo.privateType);
                publisher.publishEvent(new RabbitMqEvent(this, webSocketVo));
                return Result.OK(userRelationDto.getFriendUserId());
            }
            return  Result.FAIL(false);
    }

    /**
     * 获取用户的所有好友信息
     *
     * @param userId
     */
    @Override
    public Result findUserAllRelationAndInfo(String userId) {
        List<User> list = userMapper.selectFriendUsersByUserId(userId);
        return list != null ? Result.OK(userMapperStr.toFriendVOList(list)) :  Result.OK(Collections.emptyList()) ;
    }
}
