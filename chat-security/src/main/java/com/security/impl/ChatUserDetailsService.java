package com.security.impl;

import com.repository.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class ChatUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    // TODO 假设这是模拟用户数据，真实项目可改成查数据库
    private static final Map<String, String> USER_DATA = new HashMap<>();

    static {
        USER_DATA.put("goudan", "{noop}123456"); // {noop}表示不加密，仅用于测试
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.chat.common.model.User user = userMapper.selectByUserIdUserToSpringSecurity(username);
        String password = USER_DATA.get(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //  暂时处理一下，因为数据库里面的密码都没有加密
        user.setPassword(password);

        ChatUserDetails userDetails = new ChatUserDetails(user);

        return userDetails;
    }
}
