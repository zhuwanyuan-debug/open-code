package com.example.library.manager.backend;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.example.library.manager.backend.dao.mapper"})
@SpringBootApplication
@Slf4j
// @EnableWebMvc
public class LibraryManagerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerBackendApplication.class, args);
        log.info("\t服务启动成功！");
    }
}
