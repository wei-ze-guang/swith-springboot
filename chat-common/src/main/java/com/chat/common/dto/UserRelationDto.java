package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Schema(description = "用户关系，添加好友Dto")
public class UserRelationDto {

    /**
     * 主动方
     */
    @Schema(description = "主动添加方")
    @NotEmpty
    private String userId;
    /**
     * 被动方
     */
    @Schema(description = "被添加方")
    @NotEmpty
    private String friendUserId;

    /**
     * 好友备注
     */
    @Schema(description = "添加好友的时候的备注，或者头像名字")
    private String noteName;
}
