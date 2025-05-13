package com.chat.service.rabbitmqservice.impl;

import com.chat.common.vo.WebSocketVo;
import com.chat.service.config.RabbitMQConfig;
import com.chat.service.rabbitmqservice.RabbitMQSendPrimaryMessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class RabbitMQSendPrimaryMessageServiceImpl implements RabbitMQSendPrimaryMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate; // 自动注入 RabbitTemplate

    private ObjectMapper mapper = new ObjectMapper();
    /**
     * 这个除了群聊是通用的
     *
     * @param webSocketVo 向前端推送实时消息的类
     * @return 无返回值
     */
    @Override
    public void sendPrivateMessage(WebSocketVo webSocketVo) {
        try{
            String webSocketMessage = mapper.writeValueAsString(webSocketVo);
            rabbitTemplate.convertAndSend(RabbitMQConfig.CHAT_EXCHANGE,RabbitMQConfig.ROUTING_KEY,webSocketMessage);
        }catch (JsonProcessingException e){
            log.error("发送消息到rabbitmq抛出异常");
            e.printStackTrace();
        }


    }

    /**
     * 发送的是群信息
     *
     * @param webSocketVo
     * @retyrn 无返回值
     */
    @Override
    public void sendGroupMessage(WebSocketVo webSocketVo) {
        try{
            String webSocketMessage = mapper.writeValueAsString(webSocketVo);
            rabbitTemplate.convertAndSend(RabbitMQConfig.CHAT_EXCHANGE,RabbitMQConfig.GROUP_KEY,webSocketMessage);
        }catch (JsonProcessingException e){
            log.error("发送消息到rabbitmq抛出异常");
            e.printStackTrace();
        }

    }
}
