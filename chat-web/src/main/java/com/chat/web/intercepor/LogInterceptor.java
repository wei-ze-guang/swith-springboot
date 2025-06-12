package com.chat.web.intercepor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Deprecated
 */
public class LogInterceptor implements HandlerInterceptor {
    // 在请求处理之前进行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        long startTime = System.currentTimeMillis();
//        request.setAttribute("startTime", startTime); // 将请求的起始时间放入请求属性中
        System.out.println("Request Path: " + request.getRequestURI() );

        HttpServletRequest req = request;
        HttpServletResponse resp = response;
        String upgradeHeader = req.getHeader("Upgrade");
        String uri = req.getRequestURI();

        // ✅ 放行所有 OPTIONS 预检请求  处理跨域
        if ("OPTIONS".equalsIgnoreCase(req.getMethod()) || "websocket".equalsIgnoreCase(upgradeHeader)
                || uri.contains("/users/login")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        return true;  // 返回 true，表示请求可以继续处理，返回 false 可以阻止请求继续处理
    }

    // 在请求处理之后进行拦截
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 这里可以修改模型数据或者日志记录
    }

    // 在请求完成之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        long endTime = System.currentTimeMillis();
//        long startTime = (Long) request.getAttribute("startTime");
//        long duration = endTime - startTime;
//        System.out.println("Request Path: " + request.getRequestURI() + " completed in: " + duration + "ms");
    }
}
