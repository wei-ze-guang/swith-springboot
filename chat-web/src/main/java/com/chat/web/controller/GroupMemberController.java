package com.chat.web.controller;

import com.chat.common.dto.GroupMemberDto;
import com.chat.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-member")
@Tag(name = "群成员管理接口",description = "查看群成员，用户加群，退群")
public class GroupMemberController {

    @DeleteMapping("/exit/{userId}/{groupId}")
    @Operation(summary = "用户退出群聊,或者被踢出群")
    @ControllerLayer(value = "修改groupMember的is_deleted",module = "用户退出群聊或者被踢出群")
    public Result deleteGroupApplication(@PathVariable String userId, @PathVariable Integer groupId) {
        return Result.OK("");
    }

    @Operation(summary = "用户加入群聊",method = "用户加群")
    @ControllerLayer(value = "条件一条groupMember信息")
    @PostMapping
    public   Result addGroupApplication(GroupMemberDto groupMemberDto) {
        return Result.OK("");
    }
    @GetMapping("/groups/{userId}")
    @ControllerLayer(value = "获取用户的所有的群，包括创建和加入的",module = "用户加入和创建的所有群信息")
    @Operation(summary = "获取用户的所有的加入的群和创建的群信息")
    public Result getGroupUserJoinByOwnerId(@PathVariable String userId) {
        return Result.OK("");
    }
}
