package com.chat.common.constant;

public class DbConstant {
    /**
     * is_delete 默认
     */
    public final static  int DB_IS_DELETE_DEFAULT = 0;
    /**
     * is_delete  删除
     */
    public final static  int DB_IS_DELETE_DELETE = 1;

    /**
     * 未读
     */
    public final static  int DB_MESSAGE_STATUS_NOT_READ = 0;

    /**
     * 已读
     */
    public final static  int DB_MESSAGE_STATUS_HAS_BEEN_READ = 1;

    /**
     * 群主
     */
    public final static  int DB_GROUP_MEMBER_IDENTITY_OWNER = 1;
    /**
     * 管理员
     */
    public final static  int DB_GROUP_MEMBER_IDENTITY_MANAGER = 0;
    /**
     * 普通裙成员
     */
    public final static  int DB_GROUP_MEMBER_IDENTITY_MEMBER = 0;
}
