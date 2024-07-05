package com.uniago.security.handler;

import com.alibaba.fastjson.JSON;
import com.uniago.security.tools.ResponseResult;
import com.uniago.security.tools.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 * @author uniago
 * Create by 2024/07/04 11:26
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult<Object> result = new ResponseResult<>(HttpStatus.UNAUTHORIZED.value(), "认证失败,请重新登录");
        WebUtils.render(response, JSON.toJSONString(result));
    }

}
