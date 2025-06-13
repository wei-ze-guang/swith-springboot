package com.security.config;

import com.chat.common.utils.CaptchaUtil;
import com.chat.common.utils.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.CachedBodyHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CaptchaFilter 是一个自定义过滤器，用于拦截指定的登录和注册请求，
 * 并验证用户提交的验证码是否正确。验证码信息从请求体中提取，
 * 并通过 Redis 进行比对校验，未通过校验则直接返回错误响应。
 *
 * 应用于前后端分离架构中，确保接口的安全性，防止暴力攻击。
 */
//TODO  这里的异常可能需要处理一下
@Component
@Slf4j
@ConfigurationProperties(prefix = "captcha")
public class CaptchaFilter extends OncePerRequestFilter {

    @Value("${captcha.enable-captcha}")
    private Boolean enableCaptcha = false;

    // 需要验证码校验的路径
    //TODO  这里可以处理位yml文件读取
    private static final List<String> CAPTCHA_PATHS = List.of("/auth", "/user/register");

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CaptchaUtil captchaUtil;

    /**
     * 重写 Spring Security 提供的 OncePerRequestFilter 的核心方法，
     * 拦截特定路径的 POST 请求，校验验证码并在校验通过后放行请求。
     *
     * @param request     HTTP 请求对象
     * @param response    HTTP 响应对象
     * @param filterChain 过滤器链，用于继续执行后续过滤器
     * @throws ServletException 抛出处理异常
     * @throws IOException      抛出 IO 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //如果关闭这个功能的话直接跳过//FIXME
        if(!enableCaptcha) {
            filterChain.doFilter(request, response);
            return;
        }
        String path = request.getRequestURI();
        if ("POST".equalsIgnoreCase(request.getMethod()) && CAPTCHA_PATHS.contains(path)) {
            // 读取请求体 JSON 字符串
            String body = request.getReader().lines().collect(Collectors.joining());

            // 反序列化为 Map
            Map<String, String> jsonMap = objectMapper.readValue(body, new TypeReference<>() {});
            String uuid = jsonMap.get("uuid");
            String code = jsonMap.get("code");
            //前端会发生自动登录，加上这个之后就不需要这个了
            String key = jsonMap.get("autoLogin");
            if(key != null) {
                //自动登录的话直接放心
                // 使用缓存请求包装类重新封装请求体，防止请求体多次读取报错
                CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request, body);
                filterChain.doFilter(cachedRequest, response);
                return;
            }


            // 验证验证码
            if (!captchaUtil.isValidateCaptcha(uuid, code)) {
                log.warn("用户的验证码错误,输入uuid是{},code{}", uuid, code );
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(Result.captchaError()));
                return;
            }

            // 使用缓存请求包装类重新封装请求体，防止请求体多次读取报错
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request, body);
            filterChain.doFilter(cachedRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

