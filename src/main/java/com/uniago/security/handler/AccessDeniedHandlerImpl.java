package com.uniago.security.handler;

import com.alibaba.fastjson.JSON;
import com.uniago.security.tools.ResponseResult;
import com.uniago.security.tools.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理
 * @author uniago
 * Create by 2024/07/04 11:21
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseResult<Object> result = new ResponseResult<>(HttpStatus.FORBIDDEN.value(), "权限不足");
        WebUtils.render(response, JSON.toJSONString(result));
    }
}
