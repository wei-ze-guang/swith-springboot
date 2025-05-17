package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.PrivateMessageDto;
import com.chat.common.utils.Result;
import com.chat.service.controllerservice.PrivateMessageControllerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private-message")
@Tag(name = "privateMessage 消息相关接口")
public class PrivateMessageController {

    @Autowired
    private PrivateMessageControllerService privateMessageControllerService;

    @GetMapping("/{userId}/{toUserId}")
    @Operation(summary = "获取两个好友之间的全部私信信息")
    @ControllerLayer(value = "获取两个用户额所有就信息",module = ModuleConstant.MODULE_FIND_ALL_BOTH_SIDES_PRIVATE_MESSAGE)
    public Result getBothSidePrivateMessages(@PathVariable String userId, @PathVariable String toUserId) {
        return  privateMessageControllerService.findTwoFriendPrivateMessages(userId, toUserId);
    }

    @PostMapping
    @ControllerLayer(value = "好友发送私信但是不是在线语音视频聊天",module = ModuleConstant.MODULE_SEND_PRIVATE_MESSAGE)
    @Operation(summary = "用户发送私信")
    public Result sendPrivateMessage(@Valid @RequestBody PrivateMessageDto privateMessageDto) {
        return privateMessageControllerService.addPrivateMessage(privateMessageDto);
    }
}
