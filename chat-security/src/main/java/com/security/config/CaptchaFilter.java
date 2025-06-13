package com.security.config;

import com.chat.common.utils.CaptchaUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.jwt.CachedBodyHttpServletRequest;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    private static final List<String> CAPTCHA_PATHS = List.of("/api/auth/login", "/api/auth/register");

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private  CaptchaUtil captchaUtil ;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException, IOException {
        String path = request.getRequestURI();
        if ("POST".equalsIgnoreCase(request.getMethod()) && CAPTCHA_PATHS.contains(path)) {
            // 读取请求体中的 JSON
            String body = request.getReader().lines().collect(Collectors.joining());

            Map<String, String> jsonMap = objectMapper.readValue(body, new TypeReference<>() {});
            String uuid = jsonMap.get("uuid");
            String code = jsonMap.get("code");

            if (!captchaUtil.isValidateCaptcha(uuid, code)) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":400, \"msg\":\"验证码错误或过期\"}");
                return;
            }

            // 重写输入流（请求体已被读取）以便后续处理
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request, body);
            filterChain.doFilter(cachedRequest, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

//    private boolean validateCaptcha(String uuid, String code) {
//        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(code)) return false;
//
//        String redisKey = "captcha:" + uuid;
//        String correctCode = redisTemplate.opsForValue().get(redisKey);
//
//        redisTemplate.delete(redisKey); // 防止重复使用
//        return code.equalsIgnoreCase(correctCode);
//    }
}

