package com.chat.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "还有信息VO")
public class FriendVO {

    // 用户唯一标识  这两者没区别，这个是用户自己的好友结果
    @Schema(description = "用户唯一标识",example = "abc123")
    private String friendUserId;

    // 用户唯一标识 ,这两者没区别，这个是返回用户搜索好友的结果
    @Schema(description = "用户唯一标识",example = "abc123")
    private String userId;

    // 头像 URI，仅存相对路径，前缀由前端拼接
    @Schema(description = "用户头像uri，仅仅相对路径",example ="/first/abc.png")
    private String avatar;

    // 性别 (1: 男, 2: 女, 其他: 未设置)
    @Schema(description = "用户性别 1/男，2/女，其他/未设置")
    private Integer gender;

    // 用户昵称
    @Schema(description = "用户昵称",example = "狗蛋")
    private String nickName;

    // 用户个性签名
    @Schema(description = "用户个性签名",example = "用户个性签名")
    private String signature;
}
