package com.bjtu.movie.controller;


import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.Ratings;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.RatingsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户评分表 前端控制器
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@RestController
@RequestMapping("/ratings")
public class RatingsController {

    @Autowired
    private RatingsServiceImpl ratingsService;

    @PostMapping("/movie/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> createRating(@CurrentUser User user,@PathVariable Integer id, @RequestParam Double rating){
        ratingsService.createRating(user.getId(),id,rating);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

}
