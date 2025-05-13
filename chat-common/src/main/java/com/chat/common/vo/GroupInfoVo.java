package com.chat.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "GroupInfo  Dto")
@Data
public class GroupInfoVo {

    @Schema(description = "群id，唯一",example = "554211")
    private Long groupId;
    @Schema(description = "群主",example = "goudan")
    private String userId;
    @Schema(description = "群名字",example = "好友群")
    private String groupName;
    @Schema(description = "群公告")
    private String notice;
    @Schema(description = "群简介")
    private String introduce;
    @Schema(description = "群头像地址，相对地址",example = "/first/groupDefault.png")
    private String avatar;
    @Schema(description = "群状态  0表示正常,其他表示不正常",example = "1")
    private Integer isDeleted;
}
