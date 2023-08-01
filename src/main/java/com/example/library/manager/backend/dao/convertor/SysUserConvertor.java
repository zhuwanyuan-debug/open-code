package com.example.library.manager.backend.dao.convertor;

import com.example.library.manager.backend.controller.req.RegisterReq;
import com.example.library.manager.backend.dao.entity.SysUserEntity;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/8/1
 */
public class SysUserConvertor {
    public static SysUserEntity convertor(RegisterReq registerReq) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setAuthLevel(registerReq.getAuth());
        sysUserEntity.setEmail(registerReq.getUserName());
        sysUserEntity.setPassword(registerReq.getPassword());
        sysUserEntity.setPhone(registerReq.getPhone());
        sysUserEntity.setNickName(registerReq.getNickName());
        return sysUserEntity;
    }
}
