package com.security.handle;

import com.chat.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 用户认证成功回调
 *
 */
@Slf4j
public class HandleSecurityAuthSuccess implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request,
                                        jakarta.servlet.http.HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("onAuthenticationSuccess,用户认证成功");
        response.setContentType("application/json; charset=utf-8");
        Result result = Result.OK(true);
        String json = new ObjectMapper().writeValueAsString(result);
        response.getWriter().write(json);
    }
}
