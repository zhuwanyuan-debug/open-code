package com.example.library.manager.backend.common.constant;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/29
 */
public class RedisKey {
    private static final String BASE_KEY = "library:";

    /** 用户token存放 */
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    /** 用户token存放 */
    public static final String BOOK_NAME_STRING = "book:name:string_%s";

    public static String getKey(String key, Object... objects) {
        return BASE_KEY + String.format(key, objects);
    }
}
