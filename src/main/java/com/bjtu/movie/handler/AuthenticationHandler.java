package com.bjtu.movie.handler;

import com.alibaba.fastjson.JSON;
import com.bjtu.movie.utils.Result;
import com.bjtu.movie.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


/**
 * 异常处理
 */
@Component
public class AuthenticationHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String json = JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED.value(),"身份验证失败"));
        WebUtils.renderString(response,json);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        String json = JSON.toJSONString(Result.error(HttpStatus.UNAUTHORIZED.value(), "用户无权限操作"));
        WebUtils.renderString(response,json);
    }
}


