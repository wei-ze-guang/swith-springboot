package com.chat.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 群消息实体类
 * 对应数据库表：group_message
 */
@Data
public class GroupMessage {

    /**
     * 主键 ID（雪花算法生成）
     */
    private Long id;

    /**
     * 群 ID
     */
    private Long groupId;

    /**
     * 用户 ID（发送者）
     */
    private String userId;

    /**
     * 消息类型（0: 文本, 1: 图片, 2: 视频 等）
     */
    private Integer messageType;

    /**
     * 资源地址，如图片/视频链接
     */
    private String sourceUri;

    /**
     * 消息文本内容
     */
    private String content;

    /**
     * 消息创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 消息更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 是否逻辑删除（0: 否，1: 是）
     */
    private Integer isDeleted;

    /**
     * 消息状态（0: 已发送，1: 已读）
     */
    private Integer status;
}
