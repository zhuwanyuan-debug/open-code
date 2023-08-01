package com.example.library.manager.backend.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/31
 */
public interface RequestEnumConstants {
    @AllArgsConstructor
    @Getter
    enum RequestHeader {
        OLD_USER_TOKEN("user_token"),
        ;

        private final String name;
    }
}
