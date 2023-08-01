package com.example.library.manager.backend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.example.library.manager.backend.common.annotation.RedissonLock;
import com.example.library.manager.backend.common.constant.RedisConstants;
import com.example.library.manager.backend.common.constant.RedisKey;
import com.example.library.manager.backend.common.exception.BizErrorEnum;
import com.example.library.manager.backend.common.exception.BusinessException;
import com.example.library.manager.backend.common.utils.RedisUtils;
import com.example.library.manager.backend.dao.convertor.BookStoreConvertor;
import com.example.library.manager.backend.dao.entity.BookStoreEntity;
import com.example.library.manager.backend.dao.mapper.BookStoreEntityMapper;
import com.example.library.manager.backend.domain.dto.BookStoreDTO;
import com.example.library.manager.backend.service.BookService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService<BookStoreDTO> {

    @Resource BookStoreEntityMapper mapper;

    @Override
    public List<BookStoreDTO> selectByPage(Integer page, Integer pageSize) {
        List<BookStoreDTO> res = Lists.newArrayList();
        List<BookStoreEntity> bookStoreEntities = mapper.selectByPage((page - 1) * pageSize, pageSize);
       return bookStoreEntities.stream().map(item -> {
            BookStoreDTO bookStoreDTO = new BookStoreDTO();
            BeanUtil.copyProperties(new BookStoreDTO(), item);
            return bookStoreDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BookStoreDTO> selectByName(String name) {
        // redis缓存获取
        String redisKey = RedisKey.getKey(RedisKey.BOOK_NAME_STRING, name);
        List<BookStoreDTO> books =
                JSON.parseObject(
                        RedisUtils.get(redisKey), new TypeReference<List<BookStoreDTO>>() {});
        if (CollectionUtils.isEmpty(books)) {
            books = Lists.newArrayList();
            BeanUtil.copyProperties(books, mapper.selectByNameLike("%" + name + "%"));
            RedisUtils.set(name, JSON.toJSONString(books), RedisConstants.EXPIRE_1_DAY_SECOND);
        }
        return books;
    }

    @Override
    @RedissonLock(prefixKey = "create", key = "#name", waitTime = 5000)
    public Integer createBooks(String name, Integer count) {
        BookStoreEntity bookStoreEntity = mapper.selectByName(name);
        if (bookStoreEntity != null) {
            log.warn("已经存在{}图书,无法重新创建", name);
            throw new BusinessException(BizErrorEnum.CREATE_BOOK_ERROR);
        }
        return mapper.insertSelective(BookStoreConvertor.convertor(name, count));
    }

    @Override
    @RedissonLock(prefixKey = "delete", key = "#name", waitTime = 5000)
    public Integer deleteBooks(String name) {
        BookStoreEntity bookStoreEntity = mapper.selectByName(name);
        if (bookStoreEntity == null) {
            log.warn("不存在{}图书,无法删除", name);
            throw new BusinessException(BizErrorEnum.DELETE_STORE_ERROR);
        }
        return mapper.deleteBooksById(bookStoreEntity.getId());
    }

    @Override
    @RedissonLock(prefixKey = "update", key = "#name", waitTime = 5000)
    public Integer updateBooks(String name, Integer count) {
        BookStoreEntity bookStoreEntity = mapper.selectByName(name);
        if (bookStoreEntity == null) {
            log.warn("不存在{}图书,无法更新库存", name);
            throw new BusinessException(BizErrorEnum.UPDATE_STORE_ERROR);
        }
        // 更新库存
        bookStoreEntity.setCount(count);
        return mapper.updateByPrimaryKeySelective(bookStoreEntity);
    }
}
