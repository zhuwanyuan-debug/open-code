package com.example.library.manager.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/29
 */
@AllArgsConstructor
@Getter
public enum BizErrorEnum implements ErrorEnum {
    USER_NOT_FOUND_ERROR(-1, "系统出小差了，请稍后再试哦~~"),
    USER_PASSWORD_ERROR(-2, "参数校验失败"),
    AUTH_LOW_ERROR(-3, "权限不足"),
    CREATE_BOOK_ERROR(-4, "创建图书失败"),
    UPDATE_STORE_ERROR(-5, "更新库存失败"),
    DELETE_STORE_ERROR(-6, "删除图书失败"),
    LOCK_LIMIT(-7, "请求太频繁了，请稍后再试哦~~"),
    REGISTER_FAIL(-8, "用户注册失败"),
    ;
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
