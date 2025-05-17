package com.chat.websocket.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class WebSocketChatHandler implements WebSocketHandler {
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String) message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);
        int webSocketMessageType = jsonNode.get("type").asInt();
        /**0 是登录*/
        if (webSocketMessageType == 0) {
            String userId = jsonNode.get("userId").asText();
            doLogin(userId, session);
        } else if (webSocketMessageType == 1) {
            String userId = jsonNode.get("userId").asText();
            doLogout(userId);
        } else if (webSocketMessageType == 2) {

        } else if (webSocketMessageType == 3) {

        }else {
            log.error("错误类型");
        }


    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            sessions.values().remove(session);
            log.warn("有用户掉线");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            sessions.values().remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public  WebSocketSession getSession(String userId) {
        return sessions.get(userId);
    }

    private void doLogin(String userId, WebSocketSession session) {
        sessions.put(userId, session);
        log.info("用户:{}登录在websocket", userId);
    }
    private void doLogout(String userId) {
        sessions.remove(userId);
        log.info("用户:{} 登出websocket", userId);
    }
}


class WebSocketDTO {
    private int webSocketMessageType;
    private String userId;

    public void setWebSocketMessageType(int webSocketMessageType) {
        this.webSocketMessageType = webSocketMessageType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWebSocketMessageType() {
        return webSocketMessageType;
    }

    public String getUserId() {
        return userId;
    }
}
