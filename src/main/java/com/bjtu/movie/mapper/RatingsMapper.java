package com.bjtu.movie.mapper;

import com.bjtu.movie.entity.Ratings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户评分表 Mapper 接口
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
public interface RatingsMapper extends BaseMapper<Ratings> {

    @Select("SELECT AVG(rating) FROM ratings WHERE movie_id=#{movieId}")
    Double getRatingAvgByMovie(@Param("movieId") Integer movieId);
}
