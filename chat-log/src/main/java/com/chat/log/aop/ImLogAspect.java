package com.chat.log.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Aspect
@Component
@Slf4j
public class ImLogAspect {

    @Around("execution(public * com.chat..*(..))")  // 切所有com.chat包及子包的public方法
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求对象
        HttpServletRequest request = getHttpServletRequest();
        String ip = "unknown";
        if (request != null) {
            ip = getClientIp(request);
        }

        // 先尝试从请求头拿traceId
        String traceId = request != null ? request.getHeader("traceId") : null;
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }

        // 放入MDC，供日志框架使用
        MDC.put("traceId", traceId);
        MDC.put("ip", ip);


        // 方法签名
        String methodName = joinPoint.getSignature().toShortString();

        // 打印请求进来日志
        log.info("请求开始，方法：{}，IP：{}", methodName, ip);

        try {
            Object result = joinPoint.proceed();  // 执行目标方法
            // 打印返回日志
            log.info("请求结束，方法：{}，IP：{}", methodName, ip);
            return result;
        } catch (Throwable t) {
            log.error("请求异常，方法：" + methodName + "，IP：" + ip, t);
            throw t;
        }finally {
            MDC.clear();  // 清理，防止内存泄漏
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    /**
     * 获取客户端真实IP，处理代理情况
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // 多个IP取第一个
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index).trim();
            } else {
                return ip.trim();
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.trim();
        }
        return request.getRemoteAddr();
    }
}
