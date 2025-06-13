package com.security.jwt;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * {@code CachedBodyHttpServletRequest} 是一个自定义的 {@link HttpServletRequestWrapper}，
 * 用于缓存 HTTP 请求的 body 内容，使得请求体可以被重复读取。
 *
 * <p>在原生的 {@link HttpServletRequest} 中，输入流（InputStream）只能读取一次，
 * 如果在过滤器或拦截器中读取了请求体，后续 Controller 将无法再次读取。</p>
 *
 * <p>此类通过在构造时缓存请求体内容，然后重写 {@code getInputStream()} 和 {@code getReader()} 方法，
 * 每次都从缓存中重新构建输入流，从而实现“重复读取请求体”的能力。</p>
 *
 * <p>典型使用场景包括：</p>
 * <ul>
 *   <li>在 Spring Security 的过滤器中读取并验证请求体（如验证码）</li>
 *   <li>日志记录请求 body 内容</li>
 *   <li>自定义参数校验逻辑</li>
 * </ul>
 *
 * 示例使用：
 * <pre>{@code
 *   String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
 *   CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request, body);
 *   chain.doFilter(wrappedRequest, response);
 * }</pre>
 */
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    /**
     * 构造方法，缓存请求体内容。
     *
     * @param request 原始的 HttpServletRequest 对象
     * @param body    请求体的内容（通常是 JSON 字符串）
     */
    public CachedBodyHttpServletRequest(HttpServletRequest request, String body) {
        super(request);
        this.cachedBody = body.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 返回一个可以多次读取的 ServletInputStream，每次都从缓存中读取内容。
     */
    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        return new ServletInputStream() {
            @Override public boolean isFinished() { return byteArrayInputStream.available() == 0; }
            @Override public boolean isReady() { return true; }
            @Override public void setReadListener(ReadListener readListener) {}
            @Override public int read() { return byteArrayInputStream.read(); }
        };
    }

    /**
     * 返回一个可以多次读取的 BufferedReader，每次都从缓存中读取内容。
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
