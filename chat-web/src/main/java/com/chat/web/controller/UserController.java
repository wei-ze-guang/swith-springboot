package com.chat.web.controller;

import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;

import com.chat.service.controllerservice.UserControllerService;
import com.chat.service.controllerservice.impl.UserControllerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "用户信息模块", description = "用户信息模块")
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserControllerService userControllerService;

    @PostMapping("/register")
    @Operation(summary = "用户注册，返回boolean")
    @ControllerLayer(value = "用户注册",module = "注册")
    public Result userRegister(@RequestBody UserDto userDto) {
        log.info("进入注册控制器");
        log.info("userDto:{}", userDto);
        return userControllerService.userRegister(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录，返回userDto")
    @ControllerLayer(value = "用户登录",module = "登录")
    public Result userLogin(UserDto userDto) {
        return userControllerService.userLogin(userDto);
    }

    @PostMapping
    @Operation(summary = "根据userId批量获取用户信息")
    @ControllerLayer(value = "批量获取用户信息",module = "获取一个用户的他的全部好友")
    public Result getUsersByIds(List<String> ids) {
        return  Result.OK(new ArrayList<UserDto>());

    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "用户注销账户")
    @ControllerLayer(value = "用户注销",module = "用户注销账号")
    //TODO 这里比较麻烦，暂时不做
    public Result deleteUser(@PathVariable String userId) {
        return Result.OK(true);
    }

    @GetMapping
    @Operation(description = "根据userId关键词查询用户")
    @ControllerLayer(value = "根据关键词模糊查询结果",module = "添加好友时候搜索")
    public Result searchUserLikeAndNotFriend(String keyWord) {
        //这里并没有排除好友，是名字文通
        return userControllerService.searchUserLikeAndNotFriend(keyWord);
    }

}
