package com.bjtu.movie.mapper;

import com.bjtu.movie.entity.Ratings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT movie_id,title,a.rating,poster_path,vote_average,vote_count,release_date,runtime " +
            "FROM ratings as a,movies_metadata as b " +
            "WHERE a.user_id=#{uid} " +
            "and a.movie_id=b.id")
    List<Map<String, Object>> getMyRatingMovie(@Param("uid") Integer uid);
}
