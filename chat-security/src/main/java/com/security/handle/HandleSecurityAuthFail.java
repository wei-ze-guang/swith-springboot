package com.security.handle;

import com.chat.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * 用户认证失败回调
 */
@Slf4j
public class HandleSecurityAuthFail implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        log.info("用户认证失败");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        Result result = Result.UNAUTHORIZED();
        String json = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(json);
    }
}
