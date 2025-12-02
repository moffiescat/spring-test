package com.example.springboottest.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 启用Spring Security Web安全支持（不变）
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法级权限控制（不变）
@AllArgsConstructor // 构造器注入（不变，替代@Autowired）
public class WebSecurityConfig { // 不再继承 WebSecurityConfigurerAdapter

    private final MyUserDetailsService userDetailsService; // 自定义用户详情服务（不变）

    // 1. 配置密码编码器（提取为独立@Bean，替代原匿名内部类）
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 注意：此处保留了你原有的「明文密码比对」逻辑（仅用于测试，生产环境必须改为BCrypt等加密方式！）
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                // 加密逻辑：直接返回明文（原逻辑不变）
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                // 比对逻辑：明文直接相等判断（原逻辑不变）
                return s.equals(charSequence.toString());
            }
        };
    }

    // 2. 配置AuthenticationManager（替代原configure(AuthenticationManagerBuilder auth)）
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // 从AuthenticationConfiguration中获取AuthenticationManager，自动关联userDetailsService和passwordEncoder
        return authConfig.getAuthenticationManager();
    }

    // 3. 核心安全规则配置（替代原configure(HttpSecurity http)）
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. 请求授权规则（原authorizeRequests()替换为authorizeHttpRequests()）
                .authorizeHttpRequests(auth -> auth
                        // 其他所有请求需要身份认证（原逻辑不变）
                        .anyRequest().authenticated()
                )
                // 2. 登录配置（保持原业务逻辑：登录页、成功/失败跳转、放行登录相关接口）
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页（原逻辑不变）
                        .successForwardUrl("/") // 登录成功跳转路径（原逻辑不变）
                        .failureForwardUrl("/login?error=用户名或密码不正确") // 登录失败跳转路径（原逻辑不变）
                        // 若需自定义用户名/密码参数，取消下面注释（原逻辑不变）
                        // .usernameParameter("username")
                        // .passwordParameter("password")
                        .permitAll() // 登录相关接口放行（原逻辑不变）
                )
                // 3. 退出登录配置（原逻辑不变）
                .logout(logout -> logout
                        .permitAll()
                )
                // 4. 关闭CSRF跨域（原逻辑不变）
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // 静态资源放行（简化写法，无需 AntPathRequestMatcher）
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                // 直接传入路径字符串，Spring 自动识别为 Ant 风格匹配
                .requestMatchers("/js/**", "/css/**");
    }
}