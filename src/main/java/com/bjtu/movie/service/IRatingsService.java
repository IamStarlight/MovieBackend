package com.bjtu.movie.service;

import com.bjtu.movie.controller.dto.RatingDto;
import com.bjtu.movie.entity.Ratings;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户评分表 服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
public interface IRatingsService extends IService<Ratings> {

    Double getRatingAvgByMovie(Integer movieId);

    Ratings getRatingByIds(Integer userId, Integer movieId);

    void createRating(Integer userId, Integer movieId, Double rating);

    List<Map<String,Object>> getMyRatingMovie(Integer uid);
}
