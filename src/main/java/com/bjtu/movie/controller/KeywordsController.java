package com.bjtu.movie.controller;


import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.KeywordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 关键词管理
 */
@RestController
@RequestMapping("/keywords")
public class KeywordsController {

    @Autowired
    private KeywordsServiceImpl keywordsService;

    /**
     * 根据电影id获取关键词
     * @param id
     * @return
     */
    @GetMapping("/movies/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getKeywordsByMovieId(@PathVariable Integer id){
        return new ResponseEntity<>(Result.success(keywordsService.getKeywordsByMovieId(id)), HttpStatus.OK);
    }
}
