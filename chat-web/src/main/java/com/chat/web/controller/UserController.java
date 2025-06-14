package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;

import com.chat.service.controllerservice.UserControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "用户信息模块", description = "用户信息模块")
@RequestMapping("/user")
@Slf4j
@Validated  //使用了这个注解才能在方法上面之际使用参数校验，不然只能写在类里面
public class UserController {

    @Autowired
    private UserControllerService userControllerService;

    @PostMapping("/register")
    @Operation(summary = "用户注册，返回boolean")
    @ControllerLayer(value = "用户注册",module = ModuleConstant.MODULE_REGISTER)
    public Result userRegister(@Valid @RequestBody UserDto userDto) {
        log.info("进入注册控制器");
        log.info("userDto:{}", userDto);
        return userControllerService.userRegister(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录，返回userDto")
    @ControllerLayer(value = "用户登录",module = ModuleConstant.MODULE_LOGIN)
    public Result userLogin(@Valid @RequestBody UserDto userDto) {
        log.info("userDto:{}", userDto);
        return userControllerService.userLogin(userDto);
    }

    @PostMapping
    @Operation(summary = "根据userId批量获取用户信息")
    @ControllerLayer(value = "批量获取用户信息",module = ModuleConstant.MODULE_FIND_USER_ALL_FRIEND_INFO)
    public Result getUsersByIds(@RequestBody List<String> ids) {
        return  Result.OK(new ArrayList<UserDto>());

    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "用户注销账户")
    @ControllerLayer(value = "用户注销",module = ModuleConstant.MODULE_USER_EXIT)
    //TODO 这里比较麻烦，暂时不做
    public Result deleteUser(@PathVariable String userId) {
        return userControllerService.deleteUserById(userId);
    }

    @GetMapping("/search")
    @Operation(description = "根据userId关键词查询用户",summary = "根据userId关键词查询用户")
    @ControllerLayer(value = "根据关键词模糊查询结果",module = ModuleConstant.MODULE_FUZZY_QUERY_USER_INFO_BY_KEY_WORD)
    public Result searchUserLikeAndNotFriend(@RequestParam  String keyWord) {
        //这里并没有排除好友，是名字文通
        return userControllerService.searchUserLikeAndNotFriend(keyWord);
    }


    @GetMapping("/{userId}")
    @Operation(summary = "获取一个用户的所有信息除了密码")
    @ControllerLayer(value = "获取一个用户的所有信息除了密码",module = ModuleConstant.MODULE_FIND_ONE_USER_INFO)
    public Result getUser(@PathVariable  String userId){
        return userControllerService.findUserByUserId(userId);
    }

}
