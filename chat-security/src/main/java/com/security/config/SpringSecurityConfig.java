package com.security.config;

import com.security.handle.*;
import com.security.impl.ChatUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private ChatUserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter ;

    @Autowired
    CaptchaFilter captchaFilter ;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/user/login","/user/register","/auth","/chat","/rtc","/captcha.png","/user/**").permitAll() // 放行静态资源
                        .anyRequest().authenticated() // 其他请求都要认证
                )
                .cors(Customizer.withDefaults())   //这里他会自己去找web中配置的跨域配置
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ✅ 必须加
                .formLogin(form -> form
                        // 登录成功处理：方式二：自定义处理器（前后端分离推荐）暂时先不使用
//                        .successHandler(new HandleSecurityAuthSuccess())
                        // 登录失败处理：方式二：自定义处理器（推荐）
                        .failureHandler(new HandleSecurityAuthFail())
                )
                // 异常处理配置 —— ✅ 这里是你要的重点
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HandleAuthenticationEntryPoint()) // 未登录或token错误
                        .accessDeniedHandler(new HandleAccessDeniedHandler()) // 已登录但权限不足
                )

                .logout(logout -> logout
                        .logoutUrl("/logout") // 注销请求地址
                        .invalidateHttpSession(true) // 使 session 失效
                        .clearAuthentication(true) // 清除认证信息
                        .logoutSuccessHandler(new HandleLogoutSuccess()) // 注销成功处理（用于前后端分离）
                ).userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)

                .csrf(csrf -> csrf.disable()) // 禁用 CSRF（前后端分离通常需要禁用）
                // 5. 跨域支持（CORS，前后端分离必要）
                .cors(Customizer.withDefaults())
                //对于异常处理还需要配置
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 配置跨域策略
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("http://localhost:5173","https://www.weizeg.top","https://weizeg.top")); // 支持通配符，Spring Boot 2.4+
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // 如果前端带 Cookie，必须设置为 true
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 所有接口允许跨域
        return source;
    }


}
