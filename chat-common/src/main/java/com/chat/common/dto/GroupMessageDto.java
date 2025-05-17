package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
@Schema(description = "群信息DTO",name = "群信息DTO")
public class GroupMessageDto {


    @Schema(description = "群id",example = "454545")
    @NotNull
    private Long groupId;

    @Schema(description = "用户id",example = "466565")
    @NotEmpty
    private String userId;


    @Schema(description = "消息类型",example = "1")
    @NotNull
    private int messageType;

    @Schema(description = "资源uri",example = "/psst/p.png")
    private String sourceUri;

    @Schema(description = "文本内容",example = "hello world")
    private String content;

    @Schema(description = "一般发送是数据库默认",example = "0")
    private int isDeleted;

    @Schema(description = "消息状态/读/未读",example = "1")
    private int status;
}
