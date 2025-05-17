package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.GroupMemberDto;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.GroupMemberControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-member")
@Tag(name = "群成员管理接口",description = "查看群成员，用户加群，退群")
public class GroupMemberController {

    @Autowired
    GroupMemberControllerService groupMemberControllerService;

    @DeleteMapping("/exit/{userId}/{groupId}")
    @Operation(summary = "用户退出群聊,或者被踢出群")
    @ControllerLayer(value = "修改groupMember的is_deleted",module = ModuleConstant.MODULE_USER_EXIT_GROUP)
    public Result deleteGroupMember(@PathVariable String userId, @PathVariable Integer groupId) {
        return groupMemberControllerService.deleteGroupMember(userId, groupId);
    }

    @Operation(summary = "用户加入群聊",method = "用户加群")
    @ControllerLayer(value = "添加一条groupMember信息",module = ModuleConstant.MODULE_USER_JOIN_GROUP)
    @PostMapping
    public   Result addGroupApplication(@Valid  @RequestBody GroupMemberDto groupMemberDto) {
        return groupMemberControllerService.addGroupMember(groupMemberDto);
    }

    @GetMapping("/groups/{userId}")
    @ControllerLayer(value = "获取用户的所有的群，包括创建和加入的",module = ModuleConstant.MODULE_FIND_USER_ALL_GROUP_JOIN_INFO)
    @Operation(summary = "获取用户的所有的加入的群和创建的群信息")
    public Result getGroupUserJoinByOwnerId(@PathVariable String userId) {
        return groupMemberControllerService.findGroupsByUserId(userId);
    }

    @GetMapping("/members/{groupId}")
    @Operation(summary = "获取一个群的所有群成员信息")
    @ControllerLayer(value = "获取一个群的所有群成员信息",module = ModuleConstant.MODULE_THE_GROUP_ALL_MEMBERS_INFO)
    public Result getTheGroupAllMembersInfo(@PathVariable Integer groupId) {
        return groupMemberControllerService.findGroupAllMembersByGroupId(groupId);
    }
}
