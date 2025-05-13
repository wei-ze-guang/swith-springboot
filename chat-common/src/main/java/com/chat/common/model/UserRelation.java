package com.chat.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRelation {
    /**
     * 关键id，自增
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 好友id
     */
    private String friendUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer isDeleted;
    /**
     * 好友备注
     */
    private String noteName;
}
