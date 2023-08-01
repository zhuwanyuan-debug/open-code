package com.example.library.manager.backend.config;

import com.example.library.manager.backend.common.constant.RequestEnumConstants;
import com.example.library.manager.backend.common.context.ApiContext;
import com.example.library.manager.backend.common.exception.HttpErrorEnum;
import com.example.library.manager.backend.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/28
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    public static final String ATTRIBUTE_UID = "uid";

    @Autowired private LoginService loginService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        ApiContext.setRequest(request);
        ApiContext.setResponse(response);
        // 获取用户登录token
        String token =
                request.getHeader(RequestEnumConstants.RequestHeader.OLD_USER_TOKEN.getName());
        Integer validUid = loginService.getValidUid(token);
        // 有登录态?
        if (Objects.nonNull(validUid)) {
            // 记录用户id
            request.setAttribute(ATTRIBUTE_UID, validUid);
        } else {
            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
            return false;
        }
        return true;
    }
}
