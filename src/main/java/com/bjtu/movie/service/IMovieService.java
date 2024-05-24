package com.bjtu.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjtu.movie.model.MovieCalender;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 所有影片信息 服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
public interface IMovieService extends IService<Movie> {

    Page<Map<String,Object>> getAllMovies(Integer currentPage, Integer pageSize);

    Movie getAMovieByID(Integer id);

    Movie getAMovieByImdbID(String imdbId);

    Page<Map<String, Object>> getMoviesByIndex(Integer currentPage, Integer pageSize, String sort, String order, List<String> genres, String startYear, String endYear, Double ratingFrom, Double ratingTo, Integer votesFrom, Integer votesTo, List<String> keywords);

    void addNewMovie(Movie movie);

    void updateAMovieInfo(Movie movie);

    void deleteAMovie(Integer id);

    List<Map<String, Object>> getTopNMovie();

    List<Map<String, Object>> getMostPopularNMovie();

    Page<MovieCalender> getMovieGroupByDate(Integer currentPage, Integer pageSize);
}
