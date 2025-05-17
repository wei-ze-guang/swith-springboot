package com.chat.common.vo;


import com.chat.common.constant.WebSocketMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用来给websocket传输数据的VO")
public class WebSocketVo<T>{
    /** 私信*/
    public static final int privateType = 0;
    /**群聊*/
    public static final int publicType = 1;

    private int type ;
    /**
     * 消息类型
     */
    @Schema(description = "消息类型")
    private WebSocketMessageType messageType;

    /**
     * 消息体
     */
    @Schema(description = "消息体")
    private T messageBody;

    /**
     * 消息发送者
     */
    @Schema(description = "消息发送者")
    private String messageFrom;

    /**
     * 消息接收者
     */
    @Schema(description = "消息接收者")
    private String messageTo;
}