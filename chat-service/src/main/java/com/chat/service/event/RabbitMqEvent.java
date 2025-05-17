package com.chat.service.event;

import com.chat.common.vo.WebSocketVo;
import org.springframework.context.ApplicationEvent;


public class RabbitMqEvent extends ApplicationEvent {
    //是群还是还是私信
    private int type;
    private final  WebSocketVo  webSocketVo;

    public RabbitMqEvent(Object source, WebSocketVo webSocketVo) {
        super(source);
        this.webSocketVo = webSocketVo;
    }
    public WebSocketVo getWebSocketVo() {
        return webSocketVo;
    }
}
