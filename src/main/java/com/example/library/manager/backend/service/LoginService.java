package com.example.library.manager.backend.service;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
public interface LoginService {

    /**
     * 校验token是不是有效
     *
     * @param token
     * @return
     */
    boolean verify(String token);

    /**
     * 刷新token有效期
     *
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取token
     *
     * @param uid
     * @return 返回token
     */
    String login(Integer uid);

    /**
     * 如果token有效，返回uid
     *
     * @param token
     * @return
     */
    Integer getValidUid(String token);
}
