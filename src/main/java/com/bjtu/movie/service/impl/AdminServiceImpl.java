package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bjtu.movie.controller.dto.LoginDto;
import com.bjtu.movie.domain.Admin;
import com.bjtu.movie.constants.Role;
import com.bjtu.movie.domain.LoginAdmin;
import com.bjtu.movie.domain.LoginUser;
import com.bjtu.movie.domain.User;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.AdminMapper;
import com.bjtu.movie.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import com.bjtu.movie.utils.JwtUtil;
import com.bjtu.movie.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

//    @Autowired
//    private RedisCache redisCache;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public void initSuperAdmin() {
        //如果不存在超级管理员，创建一个
        if(!hasSuperAdmin()) {
            Admin superAdmin = new Admin();
            superAdmin.setName("超级管理员");
            superAdmin.setPassword(encodePassword("123"));
            superAdmin.setPermission(Role.ROLE_SUPER_ADMIN.getValue());
            superAdmin.setCreatedAt(DateTimeUtil.getNowTimeString());
            save(superAdmin);
        }
    }

    private String encodePassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean hasSuperAdmin(){
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getPermission, Role.ROLE_SUPER_ADMIN.getValue())
                .eq(Admin::isDeleted,false);
        Admin superAdmin = getOne(wrapper);
        return superAdmin != null;
    }

    @Override
    public List<Admin> getAllAdmin() {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getPermission, Role.ROLE_ADMIN.getValue())
                .eq(Admin::isDeleted,false);
        return listObjs(wrapper);
    }

    @Override
    public Admin getOneAdmin(String id) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getPermission, Role.ROLE_ADMIN.getValue())
                .eq(Admin::getId,id)
                .eq(Admin::isDeleted,false);
        return getOne(wrapper);
    }

    @Override
    public void deleteOneAdmin(String id) {
        if(getOneAdmin(id) == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "管理员不存在");
        }
        LambdaUpdateWrapper<Admin> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Admin::getId,id)
                .set(Admin::isDeleted,true);
        update(wrapper);
    }

    @Override
    public void resetPassword(String id, String password) {
        //todo：验证
        if(getOneAdmin(id) == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "管理员不存在");
        }
        Admin admin = new Admin();
        admin.setId(id);
        admin.setPassword(encodePassword(password));
        updateById(admin);
    }

    @Override
    public void resetInfo(String id, Admin info) {
        if(getOneAdmin(id) == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "管理员不存在");
        }
        info.setId(id);
        updateById(info);
    }

    @Override
    public Admin getByName(String name){
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName,name)
                .eq(Admin::isDeleted,false);
        return getOne(wrapper);
    }

    @Override
    public void adminRegister(Admin newAdmin) {
        if(getByName(newAdmin.getName()) != null) {
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "用户名已存在");
        }
        newAdmin.setPassword(encodePassword(newAdmin.getPassword()));
        newAdmin.setCreatedAt(DateTimeUtil.getNowTimeString());
        newAdmin.setPermission(Role.ROLE_USER.getValue());
        newAdmin.setDeleted(false);
        save(newAdmin);
    }

    public HashMap<String,String> loginAdmin(LoginDto dto) {

//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword());
//
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//
//        if(Objects.isNull(authenticate)) {
//            throw new ServiceException(HttpStatus.UNAUTHORIZED.value(), "用户名或密码错误");
//        }
//
//        //使用userid生成token
//        LoginAdmin loginAdmin = (LoginAdmin) authenticate.getPrincipal();
//        String userId = loginAdmin.getAdmin().getId();
//        String jwt = JwtUtil.createJWT(userId);
//
//        //authenticate存入redis
//        redisCache.setCacheObject("login:"+userId,loginAdmin);
//
//        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
//        Admin nowAdmin = loginAdmin.getAdmin();
//        map.put("token",jwt);
//        map.put("permission",nowAdmin.getPermission());
//        map.put("name", nowAdmin.getName());
//        map.put("id", nowAdmin.getId());
//
        return map;
    }
}
