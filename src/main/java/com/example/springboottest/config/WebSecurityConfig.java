package com.example.springboottest.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final MyUserDetailsService userDetailsService;

    // 密码编码器（生产环境替换为 BCryptPasswordEncoder！）
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 1. 放行登录页、登录失败页
                        .requestMatchers("/login", "/login?error=*").permitAll()
                        // 2. 放行静态资源
                        .requestMatchers("/js/**", "/css/**").permitAll()
                        // 3. 其他请求需认证
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页路径（与放行路径一致）
                        .successForwardUrl("/") // 登录成功跳转
                        .failureForwardUrl("/login?error=用户名或密码不正确") // 登录失败跳转
                        .permitAll() // 放行登录提交接口
                )
//                .logout(logout -> logout.permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // 失效会话
                        .deleteCookies("JSESSIONID") // 删除cookie
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}