package com.chat.common.constant;

public class ModuleConstant {
    public static final  String MODULE_LOGIN = "登录";
    public static final  String MODULE_REGISTER = "注册";
    public static final  String MODULE_USER_EXIT = "用户注销";
    public static final String MODULE_CREATE_GROUP = "创建群";
    public static final String MODULE_DISBAND_GROUP = "解散群";
    public static final String MODULE_USER_JOIN_GROUP = "用户加群";
    /**
     * 自己推出群和被踢是一样的
     */
    public static final String MODULE_USER_EXIT_GROUP = "用户退群";
    public static final String MODULE_ADD_FRIEND = "添加好友";
    public static final String MODULE_DELETE_FRIEND = "删除好友";
    public static final String MODULE_SEND_PRIVATE_MESSAGE = "用户发送私信";
    public static final String MODULE_SEND_GROUP_MESSAGE = "用户发送群信息";
    public static final String MODULE_FIND_USER_ALL_FRIEND_INFO = "获取用户所有好友信息";
    public static final String MODULE_FIND_USER_ALL_GROUP_JOIN_INFO = "获取用户所有的群信息";
    public static final String MODULE_FIND_ONE_USER_INFO = "获取一个用户的信息";
    public static final String MODULE_FIND_ONE_GROUP_INFO = "获取一个群信息";
    public static final String MODULE_FIND_ALL_BOTH_SIDES_PRIVATE_MESSAGE = "获取双方所有聊天记录";
    public static final String MODULE_FIND_ALL_ONE_GROUP_MESSAGE = "获取一个群的所有聊天记录";
    public static final String MODULE_UPLOAD_FILE_TO_MINIO = "上传文件到minio";
    public static final String MODULE_FUZZY_QUERY_USER_INFO_BY_KEY_WORD = "模糊查询好友信息";
    public static final String MODULE_FUZZY_QUERY_GROUP_INFO_BY_WORD = "模糊查询群";

    public static  final String MODULE_UPDATE_ONE_GROUP_INFO = "更新一个群";
    public static final String MODULE_UPDATE_ONE_USER_INFO = "更新一个用户信息";

    public static final String MODULE_THE_GROUP_ALL_MEMBERS_INFO = "获取一个群所有群成员信息";


}
