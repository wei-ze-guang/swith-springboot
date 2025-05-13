package com.chat.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
@Configuration
@Slf4j
public class RabbitMQConfig {

    public static final String CHAT_EXCHANGE = "chat.exchange"; // 交换机名称
    public static final String CHAT_QUEUE = "chat.primary.queue";       // 队列名称
    public static final String ROUTING_KEY = "chat.primary.key"; // 路由键
    public static final String GROUP_QUEUE  = "chat.groupmessqge.queue";
    public static final String GROUP_KEY= "chat.groupmessexchange.key";


    // 1️⃣ 声明 Direct 类型的交换机
    @Bean
    public DirectExchange chatExchange() {
        return new DirectExchange(CHAT_EXCHANGE);
    }

    // 2️⃣ 声明队列,常规队列
    @Bean
    public Queue chatQueue() {
        return new Queue(CHAT_QUEUE, true); // true 表示持久化
    }

    @Bean
    public Queue chatGroupQueue() {
        return new Queue(GROUP_QUEUE, true); // true 表示持久化
    }


    // 3️⃣ 绑定队列到交换机，指定路由键
    @Bean
    public Binding bindingChatQueue(Queue chatQueue, DirectExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingChatGroupQueue(Queue chatQueue, DirectExchange chatExchange) {
        return BindingBuilder.bind(chatGroupQueue()).to(chatExchange).with(GROUP_KEY);
    }

}
