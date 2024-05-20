package com.bjtu.movie.controller;


import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.KeywordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-19
 */
@RestController
@RequestMapping("/keywords")
public class KeywordsController {

    @Autowired
    private KeywordsServiceImpl keywordsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    public ResponseEntity<Result> getAllKeywords(){
        return new ResponseEntity<>(Result.success(keywordsService.getAllKeywords()), HttpStatus.OK);
    }
}
