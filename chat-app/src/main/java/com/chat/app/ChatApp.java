package com.chat.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com","me.doudan","com.chat.web","com.chat.service","com.chat.common"})
@MapperScan("com.repository.mapper")

public class ChatApp {

    public static void main(String[] args) {
        SpringApplication.run(ChatApp.class, args);
    }
}
