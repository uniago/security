package com.uniago.security;

import com.uniago.security.entity.User;
import com.uniago.security.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author uniago
 * Create by 2024/07/02 16:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper()
    {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
