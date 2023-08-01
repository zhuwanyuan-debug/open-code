package com.example.library.manager.backend.common.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jluzhuwanyuan@163.com
 * @date 2023/7/31
 */
public class ApiContext {
    private static ThreadLocal<HttpServletRequest> request = new InheritableThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> response = new InheritableThreadLocal<>();

    /**
     * 设置request，保存在ThreadLocal中
     *
     * @param req
     */
    public static void setRequest(HttpServletRequest req) {
        request.set(req);
    }

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest req = request.get();
        if (req == null) {
            RequestAttributes atri = RequestContextHolder.getRequestAttributes();
            if (atri != null) {
                req = ((ServletRequestAttributes) atri).getRequest();
            }
        }
        return req;
    }

    /**
     * 设置response
     *
     * @param resp response
     */
    public static void setResponse(HttpServletResponse resp) {
        response.set(resp);
    }
    /**
     * 获取response
     *
     * @return 返回response
     */
    public static HttpServletResponse getResponse() {
        return response.get();
    }
}
