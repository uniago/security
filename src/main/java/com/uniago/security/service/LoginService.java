package com.uniago.security.service;

import com.uniago.security.entity.User;
import com.uniago.security.tools.ResponseResult;

import java.util.Map;

public interface LoginService {

    ResponseResult<Map<String, String>> login(User user);

    ResponseResult<Map<String, String>> logout();

}
