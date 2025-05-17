package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Data
@Schema(description = "groupIndo dto")
public class GroupInfoDto {

    @NotNull(message = "groupInfoDto 的groupId为空")
    @Schema(description = "群id，前端生成随机的整型",example = "1234555")
    private Integer groupId;

    @NotEmpty(message = "groupInfoDto的userId为空")
    @Schema(description = "用户Id",example = "abc123")
    private String userId;

    @NotEmpty(message = "群名字不能为空")
    @Schema(description = "群名字",example = "年级群",required = true)
    private String groupName;


}
