package com.example.library.manager.backend.controller.auth;

/**
 * 权限验证
 *
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/29
 */
public @interface Auth {
    /**
     * 简单 用户权限等级
     *
     * @return
     */
    int level();
}
