package com.uniago.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uniago.security.entity.Menu;
import com.uniago.security.service.MenuService;
import com.uniago.security.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author ex_guoanjian
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2024-07-04 10:55:05
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

}




