package com.example.library.manager.backend.common.utils;

import com.example.library.manager.backend.common.exception.BizErrorEnum;
import com.example.library.manager.backend.common.exception.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author westbrook.zhu
 * @date 2023/8/1
 */
@Service
@Slf4j
public class LockUtils {

    @Resource private RedissonClient redissonClient;

    public <T> T executeWithLockThrows(
            String key, int waitTime, TimeUnit unit, SupplierThrow<T> supplier) throws Throwable {
        RLock lock = redissonClient.getLock(key);
        boolean lockSuccess = lock.tryLock(waitTime, unit);
        if (!lockSuccess) {
            throw new BusinessException(BizErrorEnum.LOCK_LIMIT);
        }
        try {
            // 执行锁内的代码逻辑
            return supplier.get();
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @SneakyThrows
    public <T> T executeWithLock(String key, int waitTime, TimeUnit unit, Supplier<T> supplier) {
        return executeWithLockThrows(key, waitTime, unit, supplier::get);
    }

    public <T> T executeWithLock(String key, Supplier<T> supplier) {
        return executeWithLock(key, -1, TimeUnit.MILLISECONDS, supplier);
    }

    @FunctionalInterface
    public interface SupplierThrow<T> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        T get() throws Throwable;
    }

    public static void main(String[] args) {
        List<String> sensitiveList = Arrays.asList("abcd", "abcbba", "adabca");
        String text = "abcdefg";
        for (String s : sensitiveList) {
            boolean hit = text.contains(s);
            System.out.println(hit);
        }
    }
}
