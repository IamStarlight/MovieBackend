package com.bjtu.movie.controller;


import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.UserServiceImpl;
import com.bjtu.movie.service.impl.WatchlistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 关注清单管理
 */
@RestController
public class WatchlistController {

    @Autowired
    private WatchlistServiceImpl watchlistService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * 加入关注列表
     * @param id
     * @param movieId
     * @return
     */
    @PostMapping("/user/{id}/watchlist")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> addToWatchlist(@PathVariable Integer id,
                                                 @RequestParam Long movieId) {
//        Integer userId = userService.getCurrentUser().getId();
        watchlistService.addToWatchlist(id,movieId);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 获取指定用户的关注列表
     * @param id
     * @return
     */
    @GetMapping("/user/{id}/watchlist")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> getWatchlist(@PathVariable Integer id) {
//        Integer userId = userService.getCurrentUser().getId();
        return new ResponseEntity<>(Result.success(watchlistService.getWatchlist(id)), HttpStatus.OK);
    }

    /**
     * 移出关注列表
     * @param id
     * @param movieId
     * @return
     */
    @DeleteMapping("/user/{id}/watchlist")
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<Result> removeOutOfWatchlist(@PathVariable Integer id,
                                                 @RequestParam Long movieId) {
        watchlistService.removeOutOfWatchlist(id,movieId);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
