package com.bjtu.movie.controller;


import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.Admin;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.constants.Role;
import com.bjtu.movie.service.impl.AdminServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import com.bjtu.movie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminServiceImpl adminService;

    /**
     * 增加一个管理员
     * @param admin
     * @return
     * @throws Exception
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Result> createOneAdmin(@RequestBody Admin admin) {
        admin.setPermission(Role.ROLE_ADMIN.getValue());
        admin.setCreatedAt(DateTimeUtil.getNowTimeString());
        return new ResponseEntity<>(Result.success(adminService.save(admin)), HttpStatus.OK);
    }

    /**
     * 获取全部管理员
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Result> getAllAdmin() {
        return new ResponseEntity<>(Result.success(adminService.getAllAdmin()), HttpStatus.OK);
    }

    /**
     * 获取一个管理员
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Result> getOneAdmin(@PathVariable String id){
        return new ResponseEntity<>(Result.success(adminService.getOneAdmin(id)), HttpStatus.OK);
    }

    /**
     * 重置别人的密码
     * @param id
     * @param password
     * @return
     */
    @PutMapping("/security/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> resetPassword(@PathVariable String id, @RequestParam String password) {
        adminService.resetPassword(id,password);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 重置自己的密码
     * @param user
     * @param password
     * @return
     */
    @PutMapping("/security")
    public ResponseEntity<Result> updateUserPassword(@CurrentUser User user, @RequestParam String password){
        adminService.resetPassword(user.getId(),password);
        return new ResponseEntity<>((Result.success()),HttpStatus.OK);
    }

    /**
     * 重置自己的信息（不包括密码）
     * @param user
     * @param info
     * @return
     */
    @PutMapping
    public ResponseEntity<Result> updateUserInfo(@CurrentUser User user, @RequestBody Admin info){
        adminService.resetInfo(user.getId(),info);
        return new ResponseEntity<>((Result.success()),HttpStatus.OK);
    }

    /**
     * 删除一个管理员
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Result> deleteOneAdmin(@PathVariable String id) {
        adminService.deleteOneAdmin(id);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

}

