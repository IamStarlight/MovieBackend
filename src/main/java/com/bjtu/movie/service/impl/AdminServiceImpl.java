package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.entity.Admin;
import com.bjtu.movie.constants.Role;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.AdminMapper;
import com.bjtu.movie.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
        wrapper.eq(Admin::getPermission, Role.ROLE_SUPER_ADMIN.getValue());
        Admin superAdmin = getOne(wrapper);
        return superAdmin != null;
    }

    @Override
    public List<Admin> getAllAdmin() {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getPermission, Role.ROLE_ADMIN.getValue());
        return listObjs(wrapper);
    }

    @Override
    public Admin getOneAdmin(String id) {
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getPermission, Role.ROLE_ADMIN.getValue())
                .eq(Admin::getId,id);
        return getOne(wrapper);
    }

    @Override
    public void deleteOneAdmin(String id) {
        if(getOneAdmin(id) == null)
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "该管理员不存在");
        removeById(id);
    }

    @Override
    public void resetPassword(String id, String password) {
        //todo：验证
        Admin admin = new Admin();
        admin.setId(id);
        admin.setPassword(encodePassword(password));
        updateById(admin);
    }

    @Override
    public void resetInfo(String id, Admin info) {
        Admin admin = getById(id);
        if(admin == null){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(),"用户不存在");
        }
        info.setId(id);
        updateById(info);
    }
}
