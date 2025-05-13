package com.chat.common.vo;


import com.chat.common.constant.WebSocketMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用来给websocket传输数据的VO")
public class WebSocketVo<T>{
    @Schema(description = "消息类型")
    /**
     * 消息类型
     */
    private WebSocketMessageType messageType;

    @Schema(description = "消息体")
    /**
     * 消息体
     */
    private T messageBody;

    @Schema(description = "消息发送者")
    /**
     * 消息发送者
     */
    private String messageFrom;

    /**
     * 消息接收者
     */
    @Schema(description = "消息接收者")
    private String messageTo;
}