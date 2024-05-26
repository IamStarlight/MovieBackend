package com.bjtu.movie.controller;


import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.Ratings;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.model.LoginUser;
import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.RatingsServiceImpl;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 评分管理
 */
@RestController
@RequestMapping("/ratings")
public class RatingsController {

    @Autowired
    private RatingsServiceImpl ratingsService;

    /**
     * 用户评分
     * @param user
     * @param id
     * @param rating
     * @return
     */
    @PostMapping("/movies/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> createRating(@CurrentUser User user,
                                               @PathVariable Long id,
                                               @RequestParam @DecimalMin("0.0") @DecimalMax("10.0") Double rating){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Integer userId = loginUser.getUser().getId();
        ratingsService.createRating(userId,id,rating);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

}
