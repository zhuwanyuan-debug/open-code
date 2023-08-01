package com.example.library.manager.backend.controller;

import com.example.library.manager.backend.common.exception.BizErrorEnum;
import com.example.library.manager.backend.controller.req.RegisterReq;
import com.example.library.manager.backend.controller.resp.ApiResult;
import com.example.library.manager.backend.domain.dto.SysUserDTO;
import com.example.library.manager.backend.service.LoginService;
import com.example.library.manager.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {
    @Resource UserService userService;
    @Resource LoginService loginService;

    @GetMapping("/login")
    @ApiOperation(value = "登录", notes = "根据用户名、密码判断该用户是否存在。存在登录成功获取token")
    public ApiResult login(
            @RequestParam("userName") @NotBlank String userName,
            @RequestParam("password") @NotBlank String password) {
        SysUserDTO userInfoByUserName = userService.getUserInfoByUserName(userName);
        if (userInfoByUserName == null) {
            return ApiResult.fail(BizErrorEnum.USER_NOT_FOUND_ERROR);
        }
        if (!password.equals(userInfoByUserName.getPassword())) {
            return ApiResult.fail(BizErrorEnum.USER_PASSWORD_ERROR);
        }
        String token = loginService.login(userInfoByUserName.getId());
        return ApiResult.success(token);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "注册", notes = "输入用户名(邮箱)、密码、手机号、昵称")
    public ApiResult register(@RequestBody RegisterReq req) {
        int num = userService.register(req);
        return num > 0 ? ApiResult.success(num) : ApiResult.fail(BizErrorEnum.REGISTER_FAIL);
    }
}
