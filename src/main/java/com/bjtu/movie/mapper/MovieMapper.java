package com.bjtu.movie.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.entity.Movie;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.movie.model.MovieCalender;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT a.id,title,b.poster_path,adult,belongs_to_collection,budget,genres,homepage,imdb_id," +
            "original_language,original_title,overview,popularity,production_companies,production_countries," +
            "release_date,revenue,runtime,spoken_languages,status,tagline,video,vote_average,vote_count " +
            "FROM movies_metadata as a left JOIN poster as b ON a.id=b.id " +
            "WHERE a.id=#{id} AND deleted=0")
    Movie getAMovieByID(@Param("id") Integer id);

    @Select("SELECT a.id,title,b.poster_path,vote_average,vote_count,release_date,runtime " +
            "FROM movies_metadata as a left JOIN poster as b ON a.id=b.id " +
            "WHERE deleted=0")
    Page<Map<String, Object>> getAllMovies(@Param("page") IPage page);

    @Select("SELECT a.id,title,b.poster_path,vote_average,vote_count,release_date,runtime " +
            "FROM movies_metadata as a LEFT JOIN poster as b ON a.id=b.id " +
            "WHERE deleted=0 ORDER BY vote_average LIMIT 100")
    List<Map<String, Object>> getTopNMovie();

    @Select("SELECT a.id,title,b.poster_path,popularity,vote_average,vote_count,release_date,runtime " +
            "FROM movies_metadata as a LEFT JOIN poster as b ON a.id=b.id " +
            "WHERE deleted=0 ORDER BY popularity LIMIT 50")
    List<Map<String, Object>> getMostPopularNMovie();
}
