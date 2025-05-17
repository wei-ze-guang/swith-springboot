package com.chat.common.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    /** 主键ID，自增 */
    private Long id;

    /** 群ID */
    private Integer groupId;

    /** 用户ID, 英文字母和数字开头 */
    private String userId;

    /** 加群时间，自动填充 */
    private LocalDateTime createdAt;

    /** 更新时间，自动更新 */
    private LocalDateTime updatedAt;

    /** 群身份：1=群主，2=管理员，0=成员，其他=其他 */
    private int  identity;

    /** 是否逻辑删除：0=正常，1=已删除 */
    private int isDeleted;
}
