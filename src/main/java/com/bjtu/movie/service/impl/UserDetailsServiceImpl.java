package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.domain.User;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.UserMapper;
import com.bjtu.movie.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService,UserDetailsPasswordService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        User user = userService.getByName(name);
        //如果查询不到数据就通过抛出异常来给出提示
        if(user == null){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(),"用户不存在");
        }
        //根据用户查询权限信息 添加到LoginUser中
        List<String> permissionKeyList =
                Collections.singletonList(user.getPermission());
        //String.valueOf(userService.getPermission(user.getId()))

        //封装成UserDetails对象返回
        return new LoginUser(user,permissionKeyList);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //对用户密码进行加密
        String encodePassword = passwordEncoder.encode(newPassword);

        //更新用户密码
        User nowUser = userService.getById(user.getUsername());
        nowUser.setPassword(encodePassword);
        userService.updateById(nowUser);

        List<String> permissionKeyList =
                Collections.singletonList(nowUser.getPermission());
        //String.valueOf(userService.getPermission(nowUser.getId()))

        return new LoginUser(nowUser,permissionKeyList);
    }
}

