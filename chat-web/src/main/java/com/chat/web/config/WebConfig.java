package com.chat.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 如果前端是localhost:5173，明确写出具体域名，不要用 "*"
                .allowedOriginPatterns("https://weizeg.top", "https://www.weizeg.top","https://8.138.190.80","http://localhost:5173")
                // 允许的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                // 允许的请求头
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "Accept")
                // 允许携带cookie
                .allowCredentials(true)
                // 预检请求的缓存时间（秒）
                .maxAge(3600);
    }
}