package com.chat.common.model;

import lombok.Data;

import java.util.Date;

/**
 * 私信消息实体类，对应数据库表 private_message
 * 用于存储用户之间发送的私信消息内容及其元信息。
 *
 * @author 狗蛋
 */
@Data
public class PrivateMessage {

    /**
     * 消息主键ID（雪花算法生成）
     */
    private Long id;

    /**
     * 发送方用户ID
     */
    private String userId;

    /**
     * 接收方用户ID
     */
    private String toUserId;

    /**
     * 消息类型（0: 文本，1: 图片，2: 视频 等）
     */
    private int messageType;

    /**
     * 资源 URI，例如图片或视频的链接
     */
    private String sourceUri;

    /**
     * 文本消息内容
     */
    private String content;

    /**
     * 发送时间（自动填充）
     */
    private Date createdAt;

    /**
     * 最后更新时间（自动更新）
     */
    private Date updatedAt;

    /**
     * 消息状态（0: 已发送，1: 已读）
     */
    private int status;

    /**
     * 是否逻辑删除（0: 正常，1: 已删除）
     */
    private int isDeleted;
}

