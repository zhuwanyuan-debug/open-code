package com.example.library.manager.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDTO {

    private Integer id;

    private String email;

    private String password;

    private String phone;

    private String nickName;

    private Integer authLevel;

    private Date createTime;

    private Date updateTime;
}
