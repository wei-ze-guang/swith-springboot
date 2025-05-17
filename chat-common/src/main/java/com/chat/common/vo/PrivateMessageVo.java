package com.chat.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "privateMessage Ov 私信相关接口 ",description = "发送信息，获取好友之家的信息")
@Data
public class PrivateMessageVo {

    @Schema(description = "消息id，唯一，服务生成")
    private Long id;

    @Schema(description = "发送方userId")

    private String userId;

    @Schema(description = "接收方")
    private String toUserId;

    @Schema(description = "类型")
    private int messageType;

    @Schema(description = "图片视频等资源的相对地址")
    private String sourceUri;
    @Schema(description = "文本消息")
    private String content;

    @Schema(description = "信息状态，已读等")
    private int status;
    @Schema(description = "是否已经删除该信息")
    private int isDeleted;
}
