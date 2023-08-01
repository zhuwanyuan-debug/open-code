package com.example.library.manager.backend.controller;

import com.example.library.manager.backend.controller.auth.Auth;
import com.example.library.manager.backend.domain.dto.BookStoreDTO;
import com.example.library.manager.backend.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@RestController
@RequestMapping("/book")
@Slf4j
@Api(tags = "图书相关接口")
public class BookController {

    @Resource private BookService<BookStoreDTO> bookService;

    @ApiOperation(value = "浏览图书", notes = "分页查看图书")
    @GetMapping("/get/books")
    public List<BookStoreDTO> getBooks(
            @NotNull @Min(1) @RequestParam(value = "page", required = false, defaultValue = "1")
                    Integer page,
            @NotNull
                    @Min(1)
                    @RequestParam(value = "pageSize", required = false, defaultValue = "20")
                    Integer pageSize) {
        log.info("分页查找图书,page={},size={}", page, pageSize);
        return bookService.selectByPage(page, pageSize);
    }

    @GetMapping("/search/books")
    @ApiOperation(value = "查找图书", notes = "根据书名 name 模糊查询，有10分钟缓存")
    public List<BookStoreDTO> searchBooks(
            @Valid @NotBlank @RequestParam(value = "name") String name) {
        return bookService.selectByName(name);
    }

    @PostMapping("/create/books")
    @ApiOperation(value = "创建图书", notes = "图书名 name 、数量 count 必填")
    @Auth(level = 10)
    public Integer createBooks(
            @Valid @NotBlank @RequestParam(value = "name") String name,
            @Valid @NotNull @RequestParam(value = "count") Integer count) {
        return bookService.createBooks(name, count);
    }

    @PostMapping("/delete/books")
    @ApiOperation(value = "删除图书", notes = "输入图书名")
    @Auth(level = 10)
    public Integer deleteBooks(@Valid @NotBlank @RequestParam String name) {
        return bookService.deleteBooks(name);
    }

    @PostMapping("/update/books")
    @ApiOperation(value = "更新图书库存", notes = "图书名 name 、数量 count 必填")
    @Auth(level = 10)
    public Integer updateBooks(
            @Valid @NotBlank @RequestParam(value = "name") String name,
            @Valid @NotNull @Min(1) @RequestParam(value = "count") Integer count) {
        return bookService.updateBooks(name, count);
    }
}
