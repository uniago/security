package com.uniago.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uniago.security.entity.LoginUser;
import com.uniago.security.entity.User;
import com.uniago.security.mapper.MenuMapper;
import com.uniago.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author uniago
 * Create by 2024/07/02 16:23
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username).eq(User::getDelFlag, 0);
        User user = userMapper.selectOne(wrapper);
        // 若查询不到数据就通过抛出异常来提示
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 根据用户查询权限信息 添加到LoginUser中
        List<String> permissions = menuMapper.selectPermsByUserId(user.getId());
        // 封装成UserDetails对象返回
        return new LoginUser(user, permissions);
    }
}
