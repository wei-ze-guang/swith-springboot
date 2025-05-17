package com.chat.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    /**
     * 这个暂时用来查询用户的所有好友使用
     */
    private String friendUserId;

    // 主键ID，自增
    private Long id;

    // 用户唯一标识
    private String userId;

    // 用户密码（加密存储）
    private String password;

    // 头像 URI，仅存相对路径，前缀由前端拼接
    private String avatar;

    // 注册时间
    private Date createdAt;

    // 更新时间
    private Date updatedAt;

    // 性别 (1: 男, 2: 女, 其他: 未设置)
    private int gender;

    // 逻辑删除标识（0: 正常, 1: 已删除）
    private int isDeleted;

    // 用户昵称
    private String nickName;

    // 用户个性签名
    private String signature;
}