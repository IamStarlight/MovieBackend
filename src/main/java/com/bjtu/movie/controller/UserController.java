package com.bjtu.movie.controller;


import com.bjtu.movie.entity.User;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.utils.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * 注册用户
     * @param newUser
     * @return
     */
    @PostMapping
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> register(@RequestBody @Valid User newUser){
        userService.register(newUser);
        return new ResponseEntity<>((Result.success()), HttpStatus.OK);
    }

    /**
     * 重置用户密码
     * @param id
     * @param password
     * @return
     */
    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> updateUserInfo(@PathVariable String id,@RequestParam String password){
        userService.resetPassword(id,password);
        return new ResponseEntity<>((Result.success()),HttpStatus.OK);
    }

    /**
     * 获取全部用户
     * @return
     */
    @GetMapping
    public ResponseEntity<Result> getAll(){
        return new ResponseEntity<>(Result.success(userService.list()), HttpStatus.OK);
    }

    /**
     * 获取一个用户
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result> getOne(@PathVariable String id){
        return new ResponseEntity<>(Result.success(userService.getById(id)), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<Result> getOneByNamE(@RequestParam(value = "name") String name){
        return new ResponseEntity<>(Result.success(userService.getByName(name)), HttpStatus.OK);
    }

}
