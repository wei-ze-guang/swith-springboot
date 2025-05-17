package com.chat.common.constant;

import lombok.Getter;

@Getter
public enum WebSocketMessageType {


    // 定义私聊枚举类型
    /**
     * 私聊消息
     * 已处理
     */
    PRIVATE_MESSAGE("好友发送来新信息，但是不是视频语音在线聊天"),
    /**
     * 私聊视频消息
     */
    PRIVATE_VIDEO_ONLINE_MESSAGE("好友发送来视频消息请求"),
    PRIVATE_VOICE_ONLINE_MESSAGE("好友发送来语音消息请求"),
    /**
     * 新好友
     * 已处理
     */
    FRIEND_NEW_FRIEND("你有新的好友"),

    /**
     * 用户删除好友
     * 已处理
     */
    FRIEND_DELETE("对方已经删除你为好友"),

    // 定义群聊枚举类型
    /**
     * 群组消息
     *
     */
    GROUP_MESSAGE("收到群组内新信息，但是不是视频语音在线聊天"),

    GROUP_VIDEO_ONLINE_MESSAGE("群组发送来视频消息"),
    GROUP_VOICE_ONLINE_MESSAGE("群组发送来语音消息"),

    /**
     * 有成员加入群聊
     * 已处理
     */
    GROUP_NEW_MEMBER("有新成员加入群组"),
    /**
     * 有成员退出群聊
     * 已处理
     */
    GROUP_MEMBER_EXIT("有成员退出群组"),

    /**
     * 群组解散
     * 已处理一半
     */
    GROUP_DESTROY("群组已解散");


    private final String description;

    WebSocketMessageType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // ✅ 打印时显示更友好的信息
    @Override
    public String toString() {
        return this.name() + " (" + this.description + ")";
    }

}
