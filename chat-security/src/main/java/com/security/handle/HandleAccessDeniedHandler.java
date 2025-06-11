package com.security.handle;

import com.chat.common.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 权限不够的时候触发
 */
public class HandleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Result result = Result.FORBIDDEN();

        String json = new ObjectMapper().writeValueAsString(result);

        response.getWriter().write(json);

        response.getWriter().write(json);
    }
}
