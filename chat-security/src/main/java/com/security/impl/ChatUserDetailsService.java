package com.security.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class ChatUserDetailsService implements UserDetailsService {
    // 假设这是模拟用户数据，真实项目可改成查数据库
    private static final Map<String, String> USER_DATA = new HashMap<>();

    static {
        USER_DATA.put("goudan", "{noop}123456"); // {noop}表示不加密，仅用于测试
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = USER_DATA.get(username);
        if (password == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return User.withUsername(username)
                .password(password)
                .roles("USER")
                .build();
    }
}
