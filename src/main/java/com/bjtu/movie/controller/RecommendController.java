package com.bjtu.movie.controller;

import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.MovieServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendController {

    @Autowired
    private MovieServiceImpl movieService;

    /**
     * 登陆时的个性推荐
     * @return
     */
    @GetMapping("/user/{id}/movie/recommend")
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getRecommendMovie(@PathVariable Integer id){
        return new ResponseEntity<>(Result.success(movieService.getRecommendMovie(id)), HttpStatus.OK);
    }

    /**
     * 未登录时的热门推荐
     * @return
     */
    @GetMapping("/movie/hot")
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getHotRecommendMovie(){
        return new ResponseEntity<>(Result.success(movieService.getHotRecommendMovie()), HttpStatus.OK);
    }

    /**
     * 电影的相关推荐
     * @return
     */
    @GetMapping("/movie/{id}/relativity")
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getRelatedRecommendMovie(@PathVariable Integer id){
        return new ResponseEntity<>(Result.success(movieService.getRelatedRecommendMovie(id)), HttpStatus.OK);
    }

}
