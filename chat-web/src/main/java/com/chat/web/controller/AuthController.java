package com.chat.web.controller;

import com.chat.common.constant.ModuleConstant;
import com.chat.common.dto.UserDto;
import com.chat.common.utils.Result;
import com.security.impl.ChatUserDetailsService;
import com.security.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private  AuthenticationManager authManager;
//    private final JwtUtil jwtUtil;
    @Autowired
    private ChatUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth")
    @Operation(summary = "springSecurity验证")
    @ControllerLayer(value = "用户登录",module = ModuleConstant.MODULE_LOGIN)
    public Map<String, String> userLogin(@Valid @RequestBody UserDto userDto) {
        // 1. 构造认证请求
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword());


        try {
            Authentication authentication = authManager.authenticate(token);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Map roleMap = new HashMap<>();

            roleMap.put("roles", userDetails.getAuthorities());

         String jwt = jwtUtil.generateToken(roleMap, userDetails.getUsername());

            return Map.of("token", jwt);
        } catch (BadCredentialsException e) {
            return Map.of("error", "用户名或密码错误");
        } catch (DisabledException e) {
            return Map.of("error", "账户已被禁用");
        } catch (Exception e) {
            return Map.of("error", "登录失败，请联系管理员");
        }
    }
}
