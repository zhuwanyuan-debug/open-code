package com.example.library.manager.backend.service;

import com.example.library.manager.backend.controller.req.RegisterReq;
import com.example.library.manager.backend.domain.dto.SysUserDTO;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
public interface UserService {

    /**
     * 获取前端展示信息
     *
     * @param userName
     * @return
     */
    SysUserDTO getUserInfoByUserName(String userName);

    /**
     * 简单注册用户
     *
     * @param registerReq
     * @return
     */
    int register(RegisterReq registerReq);
}
