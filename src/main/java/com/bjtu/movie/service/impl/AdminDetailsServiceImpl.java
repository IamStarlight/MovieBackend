package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.domain.Admin;
import com.bjtu.movie.domain.LoginAdmin;
import com.bjtu.movie.domain.User;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.AdminMapper;
import com.bjtu.movie.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public class AdminDetailsServiceImpl extends ServiceImpl<AdminMapper, Admin> implements UserDetailsService, UserDetailsPasswordService {

    @Autowired
    private AdminServiceImpl adminService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) {
        Admin admin = adminService.getByName(name);
        //如果查询不到数据就通过抛出异常来给出提示
        if(admin == null){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(),"用户不存在");
        }
        //根据用户查询权限信息 添加到LoginUser中
        List<String> permissionKeyList =
                Collections.singletonList(admin.getPermission());
        //adminService.getPermission(admin.getId())

        //封装成UserDetails对象返回
        return new LoginAdmin(admin,permissionKeyList);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //对用户密码进行加密
        String encodePassword = passwordEncoder.encode(newPassword);

        //更新用户密码
        Admin nowUser = adminService.getById(user.getUsername());
        nowUser.setPassword(encodePassword);
        adminService.updateById(nowUser);

        List<String> permissionKeyList =
                Collections.singletonList(nowUser.getPermission());
        //String.valueOf(adminService.getPermission(nowUser.getId()))

        return new LoginAdmin(nowUser,permissionKeyList);
    }
}
