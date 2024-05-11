package com.bjtu.movie.controller;


import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.utils.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-10
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 重置自己的密码
     * @param user
     * @param password
     * @return
     */
    @PutMapping("/security")
    public ResponseEntity<Result> updateUserPassword(@CurrentUser User user,@RequestParam String password){
        userService.resetPassword(user.getId(),password);
        return new ResponseEntity<>((Result.success()),HttpStatus.OK);
    }

    /**
     * 重置自己的信息（不包括密码）
     * @param user
     * @param info
     * @return
     */
    @PutMapping
    public ResponseEntity<Result> updateUserInfo(@CurrentUser User user, @RequestBody User info){
        userService.resetInfo(user.getId(),info);
        return new ResponseEntity<>((Result.success()),HttpStatus.OK);
    }

    /**
     * 获取全部用户
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getAllUser(){
        return new ResponseEntity<>(Result.success(userService.getAllUser()), HttpStatus.OK);
    }

    /**
     * 获取一个用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getOneUser(@PathVariable String id){
        return new ResponseEntity<>(Result.success(userService.getOneUser(id)), HttpStatus.OK);
    }

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> deleteOneUser(@PathVariable String id) {
        userService.deleteOneUser(id);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

}
