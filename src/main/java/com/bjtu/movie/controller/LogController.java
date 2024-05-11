package com.bjtu.movie.controller;

import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.utils.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 注册用户
     * @param newUser
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<Result> register(@RequestBody @Valid User newUser){
        userService.register(newUser);
        return new ResponseEntity<>((Result.success()), HttpStatus.OK);
    }

    /**
     * 登录用户
     * @param user
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid User user){
        return new ResponseEntity<>(Result.success(userService.login(user)), HttpStatus.OK);
    }

    /**
     * 登出用户
     * @return
     */
    @GetMapping("/logout")
    public ResponseEntity<Result> logout(){
        userService.logout();
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
