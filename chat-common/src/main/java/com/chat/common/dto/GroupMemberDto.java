package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Schema(name = "groupMember Dto",description = "群关系")
public class GroupMemberDto {

   @Schema(description = "群id，唯一")
    private Long groupId;

    @Schema(description = "群员userId")
    private String userId;

    /** 群身份：1=群主，2=管理员，0=成员，其他=其他 */
    @Schema(description = "群成员分身,1代表群主,2代表管理员,0普通用户")
    private Integer identity;

   @Schema(description = "用户在这个群的状态0表示正常,其他表示已经离开或者被离开")
    private Integer isDeleted;
}
