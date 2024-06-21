package com.bjtu.movie.controller;


import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.controller.dto.RatingDto;
import com.bjtu.movie.entity.Ratings;
import com.bjtu.movie.entity.User;
import com.bjtu.movie.model.LoginUser;
import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.RatingsServiceImpl;
import com.bjtu.movie.service.impl.UserServiceImpl;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 评分管理
 */
@RestController
public class RatingsController {

    @Autowired
    private RatingsServiceImpl ratingsService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 用户评分
     * @param mid
     * @param ratingDto
     * @return
     */
    @PostMapping("/user/{uid}/movies/{mid}/ratings")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> createRating(@PathVariable Integer uid,
                                               @PathVariable Integer mid,
                                               @RequestBody @Validated RatingDto ratingDto){
        ratingsService.createRating(uid,mid,ratingDto.getRating());
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 获取用户评分的所有电影
     * @param uid
     * @return
     */
    @GetMapping("/user/{uid}/movies/ratings")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> getMyRatingMovie(@PathVariable Integer uid){
        return new ResponseEntity<>(Result.success(ratingsService.getMyRatingMovie(uid)), HttpStatus.OK);
    }

}
