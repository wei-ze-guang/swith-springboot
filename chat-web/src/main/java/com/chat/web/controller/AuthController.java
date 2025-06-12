package com.chat.web.controller;

import cn.hutool.captcha.LineCaptcha;
import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;
import com.security.impl.ChatUserDetailsService;
import com.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ControllerLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final String SESSION_KEY = "captcha";

    @Autowired
    private  AuthenticationManager authManager;

    @Autowired
    private ChatUserDetailsService userDetailsService;

    @Autowired
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

            roleMap.put("roles", userDetails.getAuthorities());

            String jwt = jwtUtil.generateToken(roleMap, userDetails.getUsername());
            log.info("用户验证成功 accessToken:{}", jwt);
            return Result.OK(Map.of("access_token", jwt));
        } catch (BadCredentialsException e) {
            return Result.UNAUTHORIZED();
        } catch (DisabledException e) {
            return Result.UNAUTHORIZED();
        } catch (Exception e) {
            return Result.UNAUTHORIZED();
        }
    }

    @GetMapping("/captcha.png")
    public String captchaGenerate(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 创建验证码：宽 130，高 48，验证码长度 5，干扰线数量 20
        LineCaptcha captcha = new LineCaptcha(130, 48, 5, 20);

        // 保存验证码内容（小写）到 Session


        // 获取验证码内容
        String code = captcha.getCode().toLowerCase();


        log.info("验证码{}", code);

        // 设置响应类型为图片
        response.setContentType("image/png");

        // 输出图片到响应流
        captcha.write(response.getOutputStream());


        return code;
    }

}
