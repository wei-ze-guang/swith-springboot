package com.chat.service.controllerservice;

import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;
import me.doudan.doc.annotation.ServiceLayer;

import java.util.List;

/**
 * 用户控制器模块接口
 */
public interface UserControllerService {
    /**
     * 用户注册
     * 1 把信息插入数据库，插入成功返回信息，插入失败返回注册错误信息
     *
     * @param userDto
     * @return
     */
    public Result userRegister(UserDto userDto);

    /**
     * 用户登录
     * 1 验证身份
     * 2 返回jwt和个人信息
     * @param userDto
     * @return
     */
    @ServiceLayer(value = "验证密码，生成jwt等",module = "登录")
    public Result userLogin(UserDto userDto);

    /**
     * 根据userId 批量获取用户信息
     * @param ids
     * @return
     */
    public Result findUsersByids(List<String> ids);

    /**
     * 用户注销
     * 1 删除用户个人信息
     * 2删除用户私聊，群聊信息
     * 3发送信息到群聊，有用户退出群聊
     * @param  userId
     * @return Result
     */
    public Result deleteUserById(String userId);

    /**
     * 根据userId模糊查询
     * 1 查询数据得到所有数据然后返回
     * @param keyword
     * @return Result
     */

    public Result searchUserLikeAndNotFriend( String keyword);

    /**
     * 查询一个用户的信息除了密码
     * @param userId
     * @return
     */
    public Result findUserByUserId(String userId);

}
