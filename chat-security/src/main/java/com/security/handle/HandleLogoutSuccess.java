package com.security.handle;

import com.chat.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 用户注销登录，清除认证
 */
@Slf4j
public class HandleLogoutSuccess implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("用户登出成功");
        response.setContentType("application/json; charset=utf-8");
        Result result = Result.OK(true);
        String json = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(json);
    }
}
