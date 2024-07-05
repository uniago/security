CREATE database if not exists `security` default charset utf8mb4 collate utf8mb4_unicode_ci;

USE `security`;

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_name`   VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
    `nick_name`   VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
    `password`    VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
    `status`      CHAR(1)              DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
    `email`       VARCHAR(64)          DEFAULT NULL COMMENT '邮箱',
    `phone`       VARCHAR(32)          DEFAULT NULL COMMENT '手机号',
    `sex`         CHAR(1)              DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
    `avatar`      VARCHAR(128)         DEFAULT NULL COMMENT '头像',
    `user_type`   CHAR(1)     NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
    `create_by`   BIGINT(20)           DEFAULT NULL COMMENT '创建人的用户id',
    `create_time` DATETIME             DEFAULT NULL COMMENT '创建时间',
    `update_by`   BIGINT(20)           DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME             DEFAULT NULL COMMENT '更新时间',
    `del_flag`    INT(11)              DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='用户表';

-- 插入初始用户
insert into security.sys_user (id, user_name, nick_name, password, status, email, phone, sex, avatar, user_type,
                               create_by, create_time, update_by, update_time, del_flag)
values (1, 'uniago', 'uniago', '{noop}123456', '1', 'uniago.jan@gmail.com', '15798347964', '0', null, '1', 1,
        '2024-07-02 16:20:15', 1, '2024-07-02 16:20:20', 0);

-- 使用BCryptPasswordEncoder生成密码
update security.sys_user
set password = '$2a$10$6NFOCciQiN5jOrnOZMeVQO7ra6igXno8ImENm0Ofh5LpbG/yt9kFK'
where user_name = 'uniago';


-- 菜单(权限)表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `menu_name`   varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
    `path`        varchar(200)         DEFAULT NULL COMMENT '路由地址',
    `component`   varchar(255)         DEFAULT NULL COMMENT '组件路径',
    `visible`     char(1)              DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1)              DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100)         DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100)         DEFAULT '#' COMMENT '菜单图标',
    `create_by`   bigint(20)           DEFAULT NULL,
    `create_time` datetime             DEFAULT NULL,
    `update_by`   bigint(20)           DEFAULT NULL,
    `update_time` datetime             DEFAULT NULL,
    `del_flag`    int(11)              DEFAULT '0' COMMENT '是否删除（0未删除 1已删除）',
    `remark`      varchar(500)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='菜单表';

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(128) DEFAULT NULL,
    `role_key`    varchar(100) DEFAULT NULL COMMENT '角色权限字符串',
    `status`      char(1)      DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `del_flag`    int(1)       DEFAULT '0' COMMENT 'del_flag',
    `create_by`   bigint(200)  DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    `update_by`   bigint(200)  DEFAULT NULL,
    `update_time` datetime     DEFAULT NULL,
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='角色表';

-- 角色菜单关联表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `menu_id` bigint(200) NOT NULL DEFAULT '0' COMMENT '菜单id',
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;


-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `role_id` bigint(200) NOT NULL DEFAULT '0' COMMENT '角色id',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;