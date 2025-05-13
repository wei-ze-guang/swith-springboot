package com.chat.web.controller;

import com.chat.common.dto.GroupMessageDto;
import com.chat.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group-message")
public class GroupMessageController {

    @Operation(summary = "发送群信息")
    @PostMapping
    @ControllerLayer(value = "插入一条群信息",module = "发送群信息")
    public Result sendGroupMessage(GroupMessageDto groupMessageDto) {
        return null;
    }

    @GetMapping
    @Operation(summary = "根据群id获取所有的群聊天记录")
    @ControllerLayer(value = "获取一个群的的所有聊天记录",module = "获取一个群的聊天记录")
    public Result  getGroupMessagesByGroupId(String groupId) {
        return null;
    }
}
