package com.bjtu.movie.filter;

import com.alibaba.fastjson.JSONObject;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.utils.JwtUtil;
import com.bjtu.movie.utils.LoginUser;
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

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
            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(),"token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        JSONObject jsonObject = redisCache.getCacheObject(redisKey);
        LoginUser loginUser = jsonObject.toJavaObject(LoginUser.class);
        if(Objects.isNull(loginUser)){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(),"用户未登录");
        }
        //存入SecurityContextHolder
        //获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        request.setAttribute("CurrentUser",loginUser.getUser());

        //放行
        filterChain.doFilter(request, response);
    }
}

