package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Schema(description = "groupIndo dto")
public class GroupInfoDto {

    @Schema(description = "群id，前端生成随机的整型",example = "1234555")

    private Long groupId;
    @Schema(description = "用户Id",example = "abc123")
    private String userId;
    @Schema(description = "群名字",example = "年级群",required = true)
    private String groupName;


}
