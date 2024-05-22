package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.dao.MovieMapper;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.entity.Ratings;
import com.bjtu.movie.dao.RatingsMapper;
import com.bjtu.movie.service.IRatingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户评分表 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Service
public class RatingsServiceImpl extends ServiceImpl<RatingsMapper, Ratings> implements IRatingsService {

    @Autowired
    private MovieServiceImpl movieService;

    @Override
    public void createRating(Integer userId, Integer movieId, Double rating) {
        Ratings ratings = new Ratings();
        ratings.setMovieId(movieId);
        ratings.setUserId(userId);
        ratings.setRating(rating);
        ratings.setTimestamp(DateTimeUtil.createNowTimeStamp());
        saveOrUpdate(ratings);

        //更改电影总评分,添加评分人数
        LambdaQueryWrapper<Ratings> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Ratings::getAvg)
                .eq(Ratings::getMovieId,movieId);
        Ratings one = getOne(wrapper);
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setVoteAverage(one.getAvg());
        movie.setVoteCount(movieService.getAMovieByID(movieId).getVoteCount()+1);
        movieService.updateAMovieInfo(movie);
    }
}
