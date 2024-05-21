package com.bjtu.movie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.entity.Movie;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Page<Movie> getAllMovies(Integer pageSize, Integer currentPage);

    Movie getAMovieByID(Integer id);

    Movie getAMovieByImdbID(String imdbId);

    Page<Map<String, Object>> getMoviesByIndex(Integer currentPage, Integer pageSize, String sort, String order, List<String> genres, String startYear, String endYear, Double ratingFrom, Double ratingTo, Integer votesFrom, Integer votesTo, List<String> keywords);
}
