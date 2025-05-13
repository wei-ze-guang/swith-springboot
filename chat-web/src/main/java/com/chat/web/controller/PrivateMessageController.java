package com.chat.web.controller;

import com.chat.common.dto.PrivateMessageDto;
import com.chat.common.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private-message")
@Tag(name = "privateMessage 消息相关接口")
public class PrivateMessageController {

    @GetMapping("/{userId}/{toUserId}")
    @Operation(summary = "获取两个好友之间的全部私信信息")
    @ControllerLayer(value = "获取两个用户额所有就信息",module = "获取用户所有的聊天记录")
    public Result getChatRecords(@PathVariable String userId, @PathVariable String toUserId) {
        return Result.OK("");
    }

    @PostMapping
    @ControllerLayer(value = "好友发送私信但是不是在线语音视频聊天",module = "发送私信")
    @Operation(summary = "用户发送私信")
    public Result sendPrivateMessage(PrivateMessageDto privateMessageDto) {
        return Result.OK("");
    }
}
