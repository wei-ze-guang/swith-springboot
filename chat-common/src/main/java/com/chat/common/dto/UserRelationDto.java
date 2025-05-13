package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Schema(description = "用户关系，添加好友Dto")
public class UserRelationDto {

    @Schema(description = "主动添加方")
    private String userId;
    @Schema(description = "被添加方")
    private String friendUserId;

    @Schema(description = "如果是添加还有数据库自动生成")
    private Integer isDeleted;
    /**
     * 好友备注
     */
    @Schema(description = "添加好友的时候的备注，或者头像名字")
    private String noteName;
}
