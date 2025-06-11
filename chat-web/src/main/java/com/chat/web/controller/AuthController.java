package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;
import com.security.impl.ChatUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.annotation.ControllerLayer;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    private  AuthenticationManager authManager;
//    private final JwtUtil jwtUtil;
    private ChatUserDetailsService userDetailsService;

    @PostMapping("/auth")
    @Operation(summary = "springSecurity验证")
    @ControllerLayer(value = "用户登录",module = ModuleConstant.MODULE_LOGIN)
    public Map<String, String> userLogin(@Valid @RequestBody UserDto userDto) {
        // 1. 构造认证请求
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword());
//
//        // 2. 认证，并拿到认证后的结果
//        Authentication authentication = authManager.authenticate(token);
//
//        // 3. 从 authentication 中提取用户信息
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
////        // 4. 签发 JWT
////        String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//        return Map.of("token", "");

        try {
            Authentication authentication = authManager.authenticate(token);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//         String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return Map.of("token", "");
        } catch (BadCredentialsException e) {
            return Map.of("error", "用户名或密码错误");
        } catch (DisabledException e) {
            return Map.of("error", "账户已被禁用");
        } catch (Exception e) {
            return Map.of("error", "登录失败，请联系管理员");
        }
    }
}
