package com.chat.web.controller;

import com.chat.common.dto.UserRelationDto;
import com.chat.common.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relation")
@Tag(name = "用户关系管理接口",description = "用户添加好友删除好友")
public class UserRelationController {
    @PostMapping
    @ControllerLayer(value = "增加一条用户好友关系信息",module = "添加好友")
    public Result addRelation(UserRelationDto userRelationDto) {
        Result result = new Result();
        return  result;
    }

    @PutMapping
    @ControllerLayer(value = "逻辑删除一个关系信息",module = "删除好友")
    public Result deleteRelation(UserRelationDto userRelationDto) {
        return Result.OK(true);
    }
}
