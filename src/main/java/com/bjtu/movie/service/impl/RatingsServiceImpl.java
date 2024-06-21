package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.entity.Ratings;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.RatingsMapper;
import com.bjtu.movie.service.IRatingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private RatingsMapper ratingsMapper;

    @Override
    public Double getRatingAvgByMovie(Integer movieId) {
        LambdaQueryWrapper<Ratings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ratings::getMovieId, movieId)
                .last("LIMIT 1");;
        if(listObjs(wrapper) == null){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "电影不存在");
        }else{
            return ratingsMapper.getRatingAvgByMovie(movieId);
        }
    }

    @Override
    public Ratings getRatingByIds(Integer userId, Integer movieId) {
        LambdaQueryWrapper<Ratings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Ratings::getUserId,userId)
                .eq(Ratings::getMovieId,movieId);
        return getOne(wrapper);
    }


    @Override
    public void createRating(Integer userId, Integer movieId, Double rating) {
        Ratings newRating = new Ratings();
        newRating.setMovieId(movieId);
        newRating.setUserId(userId);
        newRating.setRating(rating);
        newRating.setTimestamp(DateTimeUtil.createNowTimeStamp());
        if(getRatingByIds(userId, movieId) != null) {
            LambdaUpdateWrapper<Ratings> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Ratings::getUserId,userId)
                    .eq(Ratings::getMovieId,movieId)
                    .set(Ratings::getRating,rating);
            update(wrapper);

            //更新电影评分，不需要添加评分人数
            Movie movie = new Movie();
            movie.setId(movieId);
            movie.setVoteAverage(getRatingAvgByMovie(movieId));
            movieService.updateAMovieInfo(movie);
        }else {
            save(newRating);
            //更新电影评分，添加评分人数
            movieService.updateTotalRating(userId, movieId, rating);
        }
    }

    @Override
    public List<Map<String,Object>> getMyRatingMovie(Integer uid) {
        return ratingsMapper.getMyRatingMovie(uid);
    }
}
