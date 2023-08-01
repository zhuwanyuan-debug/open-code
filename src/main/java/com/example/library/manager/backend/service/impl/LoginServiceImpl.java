package com.example.library.manager.backend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.library.manager.backend.common.constant.RedisKey;
import com.example.library.manager.backend.common.utils.JwtUtils;
import com.example.library.manager.backend.common.utils.RedisUtils;
import com.example.library.manager.backend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired private JwtUtils jwtUtils;
    // token过期时间
    private static final Integer TOKEN_EXPIRE_DAYS = 5;
    // token续期时间
    private static final Integer TOKEN_RENEWAL_DAYS = 2;

    /**
     * 校验token是不是有效
     *
     * @param token
     * @return
     */
    @Override
    public boolean verify(String token) {
        Integer uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return false;
        }
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        String realToken = RedisUtils.getStr(key);
        // 有可能token失效了，需要校验是不是和最新token一致
        return Objects.equals(token, realToken);
    }

    @Async
    @Override
    public void renewalTokenIfNecessary(String token) {
        Integer uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return;
        }
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        Long expireDays = RedisUtils.getExpire(key, TimeUnit.DAYS);
        // 不存在的key
        if (expireDays == -2) {
            return;
        }
        // 小于一天的token帮忙续期
        if (expireDays < TOKEN_RENEWAL_DAYS) {
            RedisUtils.expire(key, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    @Override
    public String login(Integer uid) {
        String key = RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
        String token = RedisUtils.getStr(key);
        if (StrUtil.isNotBlank(token)) {
            return token;
        }
        // 获取用户token,token保存在缓存，此处先不持久化、不考虑redis蹦场景
        token = jwtUtils.createToken(uid);
        // token过期用redis中心化控制，初期采用5天过期，剩1天自动续期的方案。后续可以用双token实现
        RedisUtils.set(key, token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Integer getValidUid(String token) {
        boolean verify = verify(token);
        return verify ? jwtUtils.getUidOrNull(token) : null;
    }
}
