package com.chat.common.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupInfo {
    /**
     * 表id  自增
     */
    private Long id;
    /**
     * 群id
     */
    private Long groupId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 群名字
     */
    private String groupName;
    /**
     * 群公告
     */
    private String notice;
    /**
     * 群介绍
     */
    private String introduce;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    /**
     * 群头像
     */
    private String avatar;
    /**
     * 群是否介绍，0正常 1 群已注销
     */
    private int isDeleted;
}