package com.example.library.manager.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.library.manager.backend.controller.req.RegisterReq;
import com.example.library.manager.backend.dao.convertor.SysUserConvertor;
import com.example.library.manager.backend.dao.entity.SysUserEntity;
import com.example.library.manager.backend.dao.mapper.SysUserEntityMapper;
import com.example.library.manager.backend.domain.dto.SysUserDTO;
import com.example.library.manager.backend.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource SysUserEntityMapper sysUserEntityMapper;

    @Override
    public SysUserDTO getUserInfoByUserName(String email) {
        SysUserEntity sysUserEntity = sysUserEntityMapper.selectByEmail(email);
        if (sysUserEntity == null) {
            return null;
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtil.copyProperties(sysUserEntity, sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public int register(RegisterReq registerReq) {
        SysUserEntity convertor = SysUserConvertor.convertor(registerReq);
        return sysUserEntityMapper.insertSelective(convertor);
    }
}
