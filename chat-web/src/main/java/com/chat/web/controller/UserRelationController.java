package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserRelationDto;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.UserRelationControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relation")
@Tag(name = "用户关系管理接口",description = "用户添加好友删除好友")
public class UserRelationController {
    @Autowired
    private UserRelationControllerService userRelationControllerService;

    /**
     * 已完成
     * @param userRelationDto
     * @return
     */
    @PostMapping
    @ControllerLayer(value = "增加一条用户好友关系信息",module = ModuleConstant.MODULE_ADD_FRIEND)
    @Operation(summary = "添加好友")
    public Result addRelation(@Valid @RequestBody UserRelationDto userRelationDto) {
        return  userRelationControllerService.addUserRelation(userRelationDto);
    }

    /**
     * 已完成
     * @param userRelationDto
     * @return
     */
    @PutMapping
    @ControllerLayer(value = "逻辑删除一个关系信息",module = ModuleConstant.MODULE_DELETE_FRIEND)
    @Operation(summary = "删除好友")
    public Result deleteRelation(@Valid @RequestBody UserRelationDto userRelationDto) {
        return userRelationControllerService.deleteUserRelation(userRelationDto);
    }

    /**
     * 已完成
     * @param userId
     * @return
     */
    @GetMapping
    @Operation(summary = "获取用户所有的好友信息")
    @ControllerLayer(value = "获取用户所有的好友关系",module = ModuleConstant.MODULE_FIND_USER_ALL_FRIEND_INFO)
    public Result getUserAllRelationInfo(String userId) {
        return userRelationControllerService.findUserAllRelationAndInfo(userId);
    }

}
