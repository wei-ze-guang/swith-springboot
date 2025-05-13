package com.chat.web.controller;

import com.chat.common.dto.GroupInfoDto;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.GroupInfoControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-info")
@Tag(name = "群信息管理接口",description = "创建群，删除群，更改群信息")
public class GroupInfoController {

    @Autowired
    GroupInfoControllerService groupInfoControllerService;

    @GetMapping("/{groupId}")
    @ControllerLayer(value = "根据groupId获取这个群的群信息",module = "获取群信息")
    @Operation(summary = "根据goupIg获取群信息")
    public Result getGroupById(@PathVariable Long groupId) {
        return groupInfoControllerService.findGroupInfoByGroupId(groupId);
    }

    @GetMapping("/search")
    @ControllerLayer(value = "根据群名字模糊搜索群",module = "模糊搜索群")
    @Operation(summary = "模糊搜索群")
    public Result searchGroupsByFuzzy(String keyword) {
        return groupInfoControllerService.findGroupsByKeyword(keyword);
    }

    @PostMapping
    @Operation(summary = "创建群聊")
    @ControllerLayer(value = "groupInfo插入一条数据",module = "创建群聊")
    public Result createGroup(GroupInfoDto groupInfoDto) {
        return groupInfoControllerService.addGroupInfo(groupInfoDto);
    }

    @PutMapping
    @Operation(summary = "解散群聊")
    @ControllerLayer(value = "逻辑上删除这个群",module = "界山群聊")
    public Result deleteGroupById(Integer groupId) {
        return Result.OK("");
    }
}
