package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;
import com.chat.common.utils.CaptchaUtil;
import com.security.impl.ChatUserDetailsService;
import com.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {

    @Resource
    private CaptchaUtil captchaUtil;

    private final String SESSION_KEY = "captcha";

    @Resource
    private  AuthenticationManager authManager;

    @Resource
    private ChatUserDetailsService userDetailsService;

    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    @Operation(summary = "springSecurity验证")
    @ControllerLayer(value = "用户登录",module = ModuleConstant.MODULE_LOGIN)
    public Result userLogin(@Valid @RequestBody UserDto userDto) {
        // 1. 构造认证请求
        log.info("用户的登录账号:{}", userDto.getUserId());
        log.info("用户登录密码{}",userDto.getPassword());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword());
        try {

            Authentication authentication = authManager.authenticate(token);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map roleMap = new HashMap<>();

            //  权限还没处理，使用了一个默认的
            roleMap.put("roles", userDetails.getAuthorities());

            String jwt = jwtUtil.generateToken(roleMap, userDetails.getUsername());

            log.info("用户验证成功 accessToken:{}", jwt);
            return Result.OK(Map.of("access_token", jwt));
        } catch (Exception e) {
            log.info("用户{}未认证成功",userDto.getUserId());
            return Result.UNAUTHORIZED();
        }
    }

    @GetMapping("/captcha.png")
    public Result captchaGenerate(HttpServletResponse response, HttpServletRequest request) throws IOException {

        return Result.OK(captchaUtil.captchaGenerateSaveRedis());
    }

}
