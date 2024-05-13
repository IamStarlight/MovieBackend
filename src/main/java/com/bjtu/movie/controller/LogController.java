package com.bjtu.movie.controller;

import com.bjtu.movie.controller.dto.LoginDto;
import com.bjtu.movie.domain.User;
import com.bjtu.movie.service.impl.AdminServiceImpl;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.domain.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class LogController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AdminServiceImpl adminService;

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
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody @Valid LoginDto dto){
        return new ResponseEntity<>(Result.success(userService.login(dto)), HttpStatus.OK);
    }

    /**
     * 登录管理员
     * @param dto
     * @return
     */
//    @GetMapping("/login/admin")
//    public ResponseEntity<Result> loginAdmin(@RequestBody @Valid LoginDto dto){
//        return new ResponseEntity<>(Result.success(adminService.loginAdmin(dto)), HttpStatus.OK);
//    }

    /**
     * 登出用户、管理员
     * @return
     */
    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> logout(@RequestParam String id){
        userService.logout(id);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
