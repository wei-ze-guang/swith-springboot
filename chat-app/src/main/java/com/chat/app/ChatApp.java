package com.chat.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ComponentScan({"com.chat","me.doudan","com.security","com.chat.web","com.chat.service","com.chat.common","com.chat.websocket"})
@MapperScan("com.repository.mapper")
@EnableTransactionManagement
@EnableCaching
public class ChatApp {

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }
}
