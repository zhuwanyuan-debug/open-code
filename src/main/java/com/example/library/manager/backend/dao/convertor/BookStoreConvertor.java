package com.example.library.manager.backend.dao.convertor;

import com.example.library.manager.backend.dao.entity.BookStoreEntity;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/8/1
 */
public class BookStoreConvertor {
    public static BookStoreEntity convertor(String name, Integer count) {
        BookStoreEntity bookStoreEntity = new BookStoreEntity();
        bookStoreEntity.setCount(count);
        bookStoreEntity.setName(name);
        return bookStoreEntity;
    }
}
