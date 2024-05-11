package com.bjtu.movie.controller;

import com.bjtu.movie.entity.User;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.utils.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录用户
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid User user){
        return new ResponseEntity<>(Result.success(userService.login(user)), HttpStatus.OK);
    }

    /**
     * 登出用户
     * @return
     */
    @DeleteMapping("/logout")
    public ResponseEntity<Result> logout(){
        userService.logout();
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
