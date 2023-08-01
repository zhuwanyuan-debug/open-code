package com.example.library.manager.backend.controller.req;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @NotNull
    @ApiModelProperty("用户名")
    private String userName;

    @NotNull
    @ApiModelProperty("密码")
    private String password;

    @NotNull
    @ApiModelProperty("别名")
    private String nickName;

    @NotNull
    @ApiModelProperty("电话号码")
    private String phone;

    @NotNull
    @ApiModelProperty("权限登记")
    @Builder.Default
    private Integer auth = 1  ;
}
