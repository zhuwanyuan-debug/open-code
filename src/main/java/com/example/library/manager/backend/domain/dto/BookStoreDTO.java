package com.example.library.manager.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/8/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookStoreDTO {

    private Integer id;

    private String name;

    private Integer count;

    private Date createTime;
}
