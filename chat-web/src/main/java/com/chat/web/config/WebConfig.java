package com.chat.web.config;

import com.chat.web.intercepor.LogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // 注册拦截器
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/login", "/register"); // 排除不拦截的路径
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 如果前端是localhost:5173，明确写出具体域名，不要用 "*"
                .allowedOriginPatterns("http://localhost:5173", "http://www.weizeg.top","http://8.138.190.80")
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