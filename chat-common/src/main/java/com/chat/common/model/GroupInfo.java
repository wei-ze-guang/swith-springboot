package com.chat.common.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupInfo {
    private Long id;
    private Long groupId;
    private String userId;
    private String groupName;
    private String notice;
    private String introduce;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String avatar;
    private Integer isDeleted;
}