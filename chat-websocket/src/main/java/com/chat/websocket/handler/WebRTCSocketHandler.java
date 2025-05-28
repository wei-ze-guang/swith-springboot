package com.chat.websocket.handler;

import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.vo.WebSocketVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
@Component
@Slf4j
public class WebRTCSocketHandler implements WebSocketHandler {
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * 存储用户的连接
     */
    private final static Map<String,WebSocketSession> sessions = new ConcurrentHashMap();

    private final WebSocketChatHandler webSocketChatHandler;

    public WebRTCSocketHandler(WebSocketChatHandler webSocketChatHandler) {
        this.webSocketChatHandler = webSocketChatHandler;
    }

    /**
     * 连接成功后立即
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String jsonString = (String) message.getPayload();

        WebRTCVO vo = mapper.readValue(jsonString, WebRTCVO.class);

        WebSocketVo webSocketVo = new WebSocketVo();

        if(vo == null) {
            webSocketVo.setMessageType(WebSocketMessageType.ERROR_MESSAGE);
            session.sendMessage(new TextMessage(mapper.writeValueAsString(webSocketVo)));
            return;
        }
        String callTo = vo.getCallTo();
        String type = vo.getType();
        if(callTo == null) {
            log.error("callTo is null");
            return;
        }
        switch (type){
            case "request":
                doRequest(vo);
                log.info("RTC收到字符串{}",jsonString);
                break;

            case "request-refuse", "request-agree":
                doRequestAgreeOrRefuse(session,vo);
                log.info("RTC收到字符串{}",jsonString);
                break;

            case "offer","answer","candidate":
                doOfferAndAnswerAndIce(session,vo);
                break;

            case "login":
                doLogin(session,vo.getCallFrom());
                log.info("RTC收到字符串{}",jsonString);
                log.info("用户登录在RTC:{}",vo.getCallFrom());
                break;

            default:
                log.info("RTC收到字符串{}",jsonString);
                log.warn("unknown callTo");
        }
        WebSocketSession callToSession = getSession(callTo);
//        callToSession = webSocketChatHandler.getSession(callTo);
        if(callToSession == null) {
            return;
        }
        callToSession.sendMessage(new TextMessage(jsonString));

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        sessions.values().remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("有用户自动关闭RTC webSocket");
        sessions.values().remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private WebSocketSession getSession(String id) {
        return  sessions.get(id);
    }
    /**处理请求*/
    private void doRequest(WebRTCVO webRTCVO) throws IOException {
        WebSocketVo webSocketVo = new WebSocketVo();
        webSocketVo.setMessageType(WebSocketMessageType.PRIVATE_VIDEO_ONLINE_MESSAGE);
        webSocketVo.setMessageFrom(webRTCVO.getCallFrom());
        webSocketVo.setMessageTo(webRTCVO.getCallTo());
        WebSocketSession callToSession= webSocketChatHandler.getSession(webRTCVO.getCallTo());
        callToSession.sendMessage(new TextMessage(mapper.writeValueAsString(webSocketVo))
        );
    }
    /**处理登录*/
    private void doLogin(WebSocketSession session,String userId) throws IOException {
        sessions.put(userId, session);
        Map resultLoginSuccess = Map.of("login",true);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(resultLoginSuccess)));
    }
    /**处理是否同意*/
    private void doRequestAgreeOrRefuse(WebSocketSession session,WebRTCVO webRTCVO) throws IOException {
                WebSocketSession callToSession = getSession(webRTCVO.getCallTo());
                if(callToSession != null && callToSession.isOpen()){
                    callToSession.sendMessage(new TextMessage(mapper.writeValueAsString(webRTCVO)));
                }
                else{
                    log.info("对方发送应答的时候主动方已经不在线");
                }
    }
    /**处理数据*/
    private void doOfferAndAnswerAndIce(WebSocketSession session,WebRTCVO webRTCVO) throws IOException {
        WebSocketSession callToSession = getSession(webRTCVO.getCallTo());
        if(callToSession != null && callToSession.isOpen()){
            callToSession.sendMessage(new TextMessage(mapper.writeValueAsString(webRTCVO)));
        }else {
            log.info("处理数据的时候双方出现了不在线情况");
        }
    }

}
@Data
class WebRTCVO{
    /**类型*/
    private String type;

    private Boolean isAgree;

    /**发送给谁*/
    private String callTo;

    private Map<String,Object> candidate;

    private String offer;

    private String ice;

    private String sdp;

    /**谁发起的*/
    private String callFrom;

    /**数据*/
    private String data;

}
