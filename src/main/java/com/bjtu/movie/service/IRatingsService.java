package com.bjtu.movie.service;

import com.bjtu.movie.annotation.CurrentUser;
import com.bjtu.movie.entity.Ratings;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjtu.movie.entity.User;

/**
 * <p>
 * 用户评分表 服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
public interface IRatingsService extends IService<Ratings> {

    void createRating(Integer userId, Long movieId, Double rating);

    Double getRatingAvgByMovie(Long movieId);

    Ratings getRatingByIds(Integer userId, Long movieId);
}
