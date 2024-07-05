package com.uniago.security.expression;

import com.uniago.security.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义表达式
 * 在SPEL表达式中使用 @custom相当于获取容器中bean的名字为custom的对象。然后再调用这个对象的hasAuthority方法
 * 使用: @PreAuthorize("@custom.hasAuthority('test2')")
 * @author uniago
 * Create by 2024/07/04 13:26
 */
@Component("custom")
public class CustomExpressionRoot {
    public boolean hasAuthority(String authority) {
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        // 判断用户权限集合中是否存在authority
        return permissions.contains(authority);
    }
}
