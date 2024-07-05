package com.uniago.security.mapper;

import com.uniago.security.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author ex_guoanjian
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2024-07-04 10:55:05
* @Entity com.uniago.security.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long userId);

}




