package com.chat.service.controllerservice.impl;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.UserDto;
import com.chat.common.mapstructmappr.UserMapperStr;
import com.chat.common.model.User;
import com.chat.common.vo.UserVo;
import com.chat.common.vo.WebSocketVo;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.UserControllerService;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserControllerServiceImpl implements UserControllerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private UserMapperStr userMapperStr;

    @Autowired
    private RabbitMQSendPrimaryMessageService rabbitMQSendPrimaryMessageService;
    /**
     * 用户注册
     * 1 把信息插入数据库，插入成功返回信息，插入失败返回注册错误信息
     *
     * @param userDto
     * @return
     */
    @Override
    @ServiceLayer(value = "插入数据库，返回结果",module = "注册")
    public Result userRegister(UserDto userDto) {
            User user = userMapperStr.toUser(userDto);
            userMapper.insertUser(user);
            return Result.OK(userMapperStr.toUserVo(user));
    }


    /**
     * 用户登录
     * 1 验证身份
     * 2 返回jwt和个人信息
     *
     * @param userDto
     * @return
     */
    @Override
    public Result userLogin(UserDto userDto) {
        User user = userMapperStr.toUser(userDto);
        user = userMapper.selectByUserId(user.getUserId());

        if (user == null) {
            return Result.FAIL("用户不存在");
        }

        if (!Objects.equals(userDto.getPassword(), user.getPassword())) {
            return Result.FAIL("密码不一致");
        }

        return Result.OK(userMapperStr.toUserVo(user));
    }


    /**
     * 根据userId 批量获取用户信息
     *
     * @param ids
     * @return
     */
    @Override
    public Result findUsersByids(List<String> ids) {
        return null;
    }

    /**
     * 用户注销
     * 1 删除用户个人信息
     * 2删除用户私聊，群聊信息
     * 3发送信息到群聊，有用户退出群聊
     *
     * @param userId
     * @return Result
     */
    //TODO 这里还没 完成
    @Override
    @ServiceLayer(value = "",module = "用户注销")
    public Result deleteUserById(String userId) {
        int i = userMapper.softDeleteUser(userId);
        WebSocketVo webSocketVo = new WebSocketVo();
        webSocketVo.setMessageType(WebSocketMessageType.FRIEND_DELETE);
        webSocketVo.setMessageFrom(userId);
        webSocketVo.setType(WebSocketVo.publicType);
        rabbitMQSendPrimaryMessageService.sendPrivateMessage(webSocketVo);

        return i> 0 ? Result.OK(userId) : Result.FAIL("注销失败");
    }

    /**
     * 根据userId模糊查询
     * 1 查询数据得到所有数据然后返回
     *  没有的话返回空的
     * @param keyword
     * @return Result
     */
    @Override
    @ServiceLayer(value = "根据关键字查找好友",module = "关键字模糊查找好友")
    public Result searchUserLikeAndNotFriend(String keyword) {
        try{
            List<User> userList = userMapper.selectByUserIdByLike(keyword);
            //如果非空就返回，空的话返回一个新数组
            return Result.OK(Objects.requireNonNullElseGet(userMapperStr.toVOList(userList), () -> Collections.emptyList()));
        }catch (Exception e){
            e.printStackTrace();
            //返回空数组
            return Result.OK(Collections.emptyList());
        }
    }

    /**
     * 查询一个用户的信息除了密码
     *
     * @param userId
     * @return
     */
    @Override
    public Result findUserByUserId(String userId) {
        User user = userMapper.selectOneByUserId(userId);
        return user != null ? Result.OK(userMapperStr.toUserVo(user)) : Result.FAIL(false);
    }
}
