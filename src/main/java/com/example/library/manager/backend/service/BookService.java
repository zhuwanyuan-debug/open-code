package com.example.library.manager.backend.service;

import java.util.List;
/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
public interface BookService<T> {

    public List<T> selectByPage(Integer page, Integer pageSize);

    public List<T> selectByName(String name);

    public Integer createBooks(String name, Integer count);

    public Integer deleteBooks(String name);

    public Integer updateBooks(String name, Integer count);
}
