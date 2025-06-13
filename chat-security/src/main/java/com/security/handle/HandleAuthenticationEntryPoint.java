package com.security.handle;

import com.chat.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 未认证，或者发生异常
 */
public class HandleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        Result result = Result.UNAUTHORIZED();
        String json = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(json);
    }
}
