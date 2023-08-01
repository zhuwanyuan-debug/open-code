create table sys_user
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(32) NOT NULL COMMENT '用户名，（邮箱）',
    PASSWORD VARCHAR(64) NULL,
    phone VARCHAR(16) DEFAULT '',
    nick_name VARCHAR(64) DEFAULT '',
    auth_level int(4) NOT NULL DEFAULT '1' COMMENT '权限等级 1:普通用户 10:管理员',
    create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY `phone` (`phone`),
    KEY `ix_created_at` (`create_time`),
    KEY `ix_updated_at` (`update_time`)
);