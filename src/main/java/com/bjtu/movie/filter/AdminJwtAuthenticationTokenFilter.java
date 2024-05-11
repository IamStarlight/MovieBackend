package com.bjtu.movie.filter;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.movie.domain.LoginAdmin;
import com.bjtu.movie.domain.LoginUser;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.utils.JwtUtil;
import com.bjtu.movie.utils.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AdminJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("Token");
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(),"Token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        JSONObject jsonObject = redisCache.getCacheObject(redisKey);
        LoginAdmin loginAdmin = jsonObject.toJavaObject(LoginAdmin.class);
        if(Objects.isNull(loginAdmin)){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(),"用户未登录");
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginAdmin,null,loginAdmin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        request.setAttribute("CurrentUser",loginAdmin.getAdmin());

        //放行
        filterChain.doFilter(request, response);
    }
}
