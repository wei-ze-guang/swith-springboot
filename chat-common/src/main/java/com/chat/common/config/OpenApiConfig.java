package com.chat.common.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.AnnotationHelper;
import me.doudan.doc.annotation.ControllerLayer;
import me.doudan.doc.annotation.DataLayer;
import me.doudan.doc.annotation.ManagementLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@ComponentScan(basePackages = "com.chat.common")
@Configuration
public class OpenApiConfig {

    @Autowired
    @Lazy
    private AnnotationHelper annotationHelper;
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info().title("First Chat API")
                        .description("first chat API")
                        .version("3.1.0")
                        .contact(new Contact().name("ZeGuangWei").email("705204898@qq.com")));
    }

    @PostConstruct
    public void init() throws IOException {
        log.info("开始");
        try{
            annotationHelper.scan("com");
            annotationHelper.writeModuleMd("myself.md");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
