package com.bjtu.movie.mapper;

import com.bjtu.movie.entity.Watchlist;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 关注清单 Mapper 接口
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-26
 */
public interface WatchlistMapper extends BaseMapper<Watchlist> {

    @Select("SELECT id,title,vote_average,vote_count,release_date,runtime FROM movies_metadata " +
            "where id IN (SELECT movie_id FROM watchlist where user_id = #{userId})")
    List<Map<String, Object>> getWatchlist(@Param("userId") Integer userId);
}
