package com.chat.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author 86199
 */
@Data
@Schema(name = "groupMember Dto",description = "群关系")
public class GroupMemberDto {

   @Schema(description = "群id，唯一")
   @Nonnull
    private Long groupId;

   @NotEmpty
    @Schema(description = "群员userId",example = "45685232")
    private String userId;

    /** 群身份：1=群主，2=管理员，0=成员，其他=其他 */
    @Schema(description = "群成员分身,1代表群主,2代表管理员,0普通用户",example = "1")
    private int identity;

}
