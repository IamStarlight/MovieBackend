package com.bjtu.movie.controller;


import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.CreditsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 演职人员信息管理
 */
@RestController
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    private CreditsServiceImpl creditsService;

    /**
     * 根据电影id获取演职人员信息
     * @param id
     * @return
     */
    @GetMapping("/movies/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getCreditsByMovieId(@PathVariable String id){
        return new ResponseEntity<>(Result.success(creditsService.getCreditsByMovieId(id)), HttpStatus.OK);
    }

}
