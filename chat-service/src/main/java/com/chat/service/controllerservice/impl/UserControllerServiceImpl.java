package com.chat.service.controllerservice.impl;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.dto.UserDto;
import com.chat.common.model.User;
import com.chat.common.vo.WebSocketVo;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.UserControllerService;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserControllerServiceImpl implements UserControllerService {

    @Autowired
    private UserMapper userMapper;

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
        try {
            User user = new User();
            user.setUserId(userDto.getUserId());
            user.setPassword(userDto.getPassword());
            userMapper.insertUser(user);
            return Result.OK(user);
        } catch (DuplicateKeyException e) {
            log.info("好友注册失败，主键问题，无法插入");
            return Result.FAIL("用户ID已存在，请更换后再试");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入数据库异常");
            return Result.FAIL("注册失败：" + e.getMessage());
        }
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
    //TODO 处理jwt
    public Result userLogin(UserDto userDto) {
        try{
            User user = userMapper.selectByUserId(userDto.getUserId());
            if(user==null){
            throw  new RuntimeException("查询的用户不存在");
            }
            if(user.getPassword().equals(userDto.getPassword())){
                return Result.OK(user);
            }
            return Result.FAIL("密码不一致");
        }

        catch (RuntimeException e){

        e.printStackTrace();

        return Result.FAIL("查询数据库出错");
    }
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
    @Override
    public Result deleteUserById(String userId) {
        try{
            try{
                userMapper.softDeleteUser(userId);
            }catch (Exception e){
                e.printStackTrace();
            }
            WebSocketVo webSocketVo = new WebSocketVo();
            webSocketVo.setMessageType(WebSocketMessageType.FRIEND_DELETE);
            webSocketVo.setMessageFrom(userId);
            rabbitMQSendPrimaryMessageService.sendPrivateMessage(webSocketVo);

        }catch (RuntimeException e){

        }
        return null;
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
            return Result.OK(Objects.requireNonNullElseGet(userList, () -> new ArrayList<User>()));
        }catch (Exception e){
            e.printStackTrace();
            //返回空数组
            return Result.OK(new ArrayList<User>());
        }
    }
}
