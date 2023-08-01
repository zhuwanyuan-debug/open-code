package com.example.library.manager.backend.controller.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证
 *
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/29
 */
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
@Target(ElementType.METHOD) // 作用在方法上
public @interface Auth {
    /**
     * 简单 用户权限等级
     *
     * @return
     */
    int level();
}
