package com.bjtu.movie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bjtu.movie.entity.User;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-10
 */
public interface IUserService extends IService<User> {

    String getPermission(String id);

    void register(User newUser);

    void logout();

    HashMap<String,String> login(User user);

    User getByName(String name);

    void resetPassword(String id, String password);

    void resetInfo(String id, User info);

    List<User> getAllUser();

    User getOneUser(String id);

    void deleteOneUser(String id);
}
