package com.chat.service.rabbitmqservice.listenner;

import com.chat.common.constant.RedisCacheConstant;
import com.chat.common.constant.WebSocketMessageType;
import com.chat.common.vo.WebSocketVo;
import com.chat.service.config.RabbitMQConfig;
import com.chat.service.managerservice.WebSocketDataService;
import com.chat.websocket.handler.WebSocketChatHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
@Component
public class RabbitMQConsumer {
    @Autowired
    WebSocketChatHandler webSocketChatHandler;
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebSocketDataService webSocketDataService;

    @Autowired
    private CacheManager cacheManager;


    /**
     * 接收chat.queue消息队列的消息，并将消息发送到WebSocket客户端,这是私信
     * @param message 消息内容
     * @return void
     */
    @RabbitListener(queues = RabbitMQConfig.CHAT_QUEUE)
    public void receiveQueue(String message) {
        log.info("\uD83D\uDE80 开始消费chat.queue消息队列 收到消息长度：{}", message.length());
        WebSocketVo webSocketVo = null;
        try {
            try{
                webSocketVo = objectMapper.readValue(message, WebSocketVo.class);
            } catch (Exception e) {
                log.error("消费chat.queue消息队列 解析消息失败：{}");
                e.printStackTrace();
            }
            WebSocketSession session = webSocketChatHandler.getSession(webSocketVo.getMessageTo());
            if (session!= null) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(webSocketVo)));
                log.info("\uD83D\uDE80 消费信息结果已完成，对方在线，发送消息成功");
            }
            else {
                log.info("\uD83D\uDE80 消费信息结果已完成，对方不在线，不发送消息");
            }
        } catch (Exception e) {
            log.error("消费chat.queue消息队列 消费信息结果已完成，发送消息失败：{}", e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 这个是监听群的
     * @param message
     */
    @RabbitListener(queues = RabbitMQConfig.GROUP_QUEUE)
    public void receiveQueue1(String message) {
        log.info("\uD83D\uDE80 开始消费chat.queue.relationship消息队列 收到消息长度：{}", message.length());
        WebSocketVo webSocketVo = null;
        try {
            try{
                webSocketVo = objectMapper.readValue(message, WebSocketVo.class);
            } catch (Exception e) {
                log.error("消费chat.queue.relationship消息队列 解析消息失败：{}");
                e.printStackTrace();
            }
            //这里还需要处理看一下是什么
            assert webSocketVo != null;
            Integer toGroup = null;
            try{
               toGroup= Integer.valueOf(webSocketVo.getMessageTo());
            }catch (Exception e){
                log.error(e.getMessage());
                log.error("错误的群号");
                return;
            }

            List<String> list = webSocketDataService.getTheGroupMembersOnlyUserIdList(toGroup);
            log.info("群员数量:{}", list.size());
            for(String item : list) {
                WebSocketSession session = webSocketChatHandler.getSession(item);
                if (session!= null) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(webSocketVo)));
                    log.info("\uD83D\uDE80 消费信息结果已完成，对方在线，发送消息成功");
                }
                else {
                    log.info("\uD83D\uDE80 消费信息结果已完成，对方不在线，不发送消息");
                }
            }
            //这里先测试一下
            if(webSocketVo.getMessageType() == WebSocketMessageType.GROUP_DESTROY){
                cacheManager.getCache(RedisCacheConstant.CACHE_NAME).evict(toGroup);
            }

        } catch (Exception e) {
            log.error("消费chat.queue消息队列 消费信息结果已完成，发送消息失败：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
