package com.bjtu.movie.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.movie.model.MovieCalender;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 所有影片信息 Mapper 接口
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
public interface MovieMapper extends BaseMapper<Movie> {
    @Select("SELECT release_date as date,COUNT(*) as number,GROUP_CONCAT(id) as ids from movies_metadata " +
            "GROUP BY release_date " +
            "ORDER BY release_date DESC")
    Page<MovieCalender> getMovieGroupByDate(@Param("page") IPage page);

}
