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

@RestController
@RequestMapping("/credits")
public class CreditsController {

    @Autowired
    private CreditsServiceImpl creditsService;

    @GetMapping("/movie/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getCreditsByMovieId(@PathVariable String id){
        return new ResponseEntity<>(Result.success(creditsService.getCreditsByMovieId(id)), HttpStatus.OK);
    }

}
