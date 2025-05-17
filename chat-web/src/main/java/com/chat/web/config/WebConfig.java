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
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(false)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                // 设置允许的header属性
                .allowedHeaders("*")
                .allowedHeaders("Authorization", "Content-Type") // ✅ 允许自定义头
                // 跨域允许时间
                .maxAge(3600).allowCredentials(true);
    }


}