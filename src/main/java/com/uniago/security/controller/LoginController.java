package com.uniago.security.controller;

import com.uniago.security.entity.User;
import com.uniago.security.service.LoginService;
import com.uniago.security.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author uniago
 * Create by 2024/07/02 16:54
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult<Map<String, String>> login(@RequestBody User user) {
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult<Map<String, String>> login() {
        return loginService.logout();
    }
}
