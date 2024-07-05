package com.uniago.security.config;

import com.uniago.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author uniago
 * Create by 2024/07/02 16:42
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // // 表单登录
                // .formLogin()
                // // .loginProcessingUrl("/user/login")
                // // 认证成功
                // .successHandler(new AuthenticationSuccessHandlerImpl())
                // // 认证失败
                // .failureHandler(new AuthenticationFailureHandlerImpl())
                // .and()
                // 注销
                .logout()
                // 注销成功处理
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())

                .and()
                // 授权访问限制
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                // 限制权限访问
                // .antMatchers("/hello").hasAuthority("test")
                // 允许匿名和携带token访问
                .antMatchers("/test").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()

                .and()
                // 异常处理
                .exceptionHandling()
                // 403 授权失败处理类
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                // 401 未授权处理类
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())

                .and()
                // 把token校验过滤器添加到过滤连中
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // 不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                // 关闭csrf
                .csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
