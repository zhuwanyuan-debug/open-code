package com.example.library.manager.backend.controller.auth;

import com.example.library.manager.backend.common.constant.RequestEnumConstants;
import com.example.library.manager.backend.common.context.ApiContext;
import com.example.library.manager.backend.common.exception.BizErrorEnum;
import com.example.library.manager.backend.common.exception.BusinessException;
import com.example.library.manager.backend.dao.entity.SysUserEntity;
import com.example.library.manager.backend.dao.mapper.SysUserEntityMapper;
import com.example.library.manager.backend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/29
 */
@Slf4j
@Aspect
@Component
@Order(0) // 确保比事务注解先执行，分布式锁在事务外
public class AuthAspect {
    @Resource private SysUserEntityMapper sysUserEntityMapper;

    @Resource private LoginService loginService;

    @Around("@annotation(com.example.library.manager.backend.controller.auth.Auth)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Auth redissonLock = method.getAnnotation(Auth.class);
        // 获取用户登录token
        String token =
                ApiContext.getRequest()
                        .getHeader(RequestEnumConstants.RequestHeader.OLD_USER_TOKEN.getName());
        // 权限校验
        SysUserEntity sysUserEntity =
                sysUserEntityMapper.selectByPrimaryKey(loginService.getValidUid(token));
        if (sysUserEntity == null || redissonLock.level() > sysUserEntity.getAuthLevel()) {
            throw new BusinessException(BizErrorEnum.AUTH_LOW_ERROR);
        }
        return joinPoint.proceed();
    }
}
