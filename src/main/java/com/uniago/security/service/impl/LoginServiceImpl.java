package com.uniago.security.service.impl;

import com.uniago.security.entity.LoginUser;
import com.uniago.security.entity.User;
import com.uniago.security.service.LoginService;
import com.uniago.security.tools.JwtUtil;
import com.uniago.security.tools.RedisCache;
import com.uniago.security.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author uniago
 * Create by 2024/07/02 16:58
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache<LoginUser> redisCache;

    @Override
    public ResponseResult<Map<String, String>> login(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 使用userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // authenticate存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);
        // 把token响应给前端
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult<>(200, "登录成功", map);
    }

    @Override
    public ResponseResult<Map<String, String>> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:" + userId);
        return new ResponseResult<>(200, "注销成功");
    }
}
