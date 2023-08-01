package com.example.library.manager.backend.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.example.library.manager.backend.common.annotation.RedissonLock;
import com.example.library.manager.backend.common.utils.LockUtils;
import com.example.library.manager.backend.common.utils.SpElUtils;
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
 * @date 2023/8/1
 */
@Slf4j
@Aspect
@Component
// 确保比事务注解先执行，分布式锁在事务外
@Order(0)
public class RedissonLockAspect {
    @Resource private LockUtils lockUtils;

    @Around("@annotation(com.example.library.manager.backend.common.annotation.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        // 默认方法限定名+注解排名（可能多个
        String prefix =
                StrUtil.isBlank(redissonLock.prefixKey())
                        ? SpElUtils.getMethodKey(method)
                        : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockUtils.executeWithLockThrows(
                prefix + ":" + key,
                redissonLock.waitTime(),
                redissonLock.unit(),
                joinPoint::proceed);
    }
}
