package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.GroupMessageDto;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.GroupMessageControllerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-message")
public class GroupMessageController {

    @Autowired
    GroupMessageControllerService groupMessageControllerService;

    @Operation(summary = "发送群信息")
    @PostMapping
    @ControllerLayer(value = "插入一条群信息",module = ModuleConstant.MODULE_SEND_GROUP_MESSAGE)
    public Result sendGroupMessage(@Valid @RequestBody GroupMessageDto groupMessageDto) {
        return groupMessageControllerService.addGroupMessage(groupMessageDto);
    }

    @GetMapping
    @Operation(summary = "根据群id获取所有的群聊天记录")
    @ControllerLayer(value = "获取一个群的的所有聊天记录",module = ModuleConstant.MODULE_FIND_ALL_ONE_GROUP_MESSAGE)
    public Result  getGroupMessagesByGroupId(@RequestParam Integer groupId) {
        return groupMessageControllerService.findGroupMessageByGroupId(groupId);
    }
}
