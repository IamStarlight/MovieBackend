package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.constants.Sort;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.dao.MovieMapper;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.service.IMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 所有影片信息 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements IMovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public Page<Movie> getAllMovies(Integer pageSize, Integer currentPage) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::isDeleted,false);
        //List<Employee> employeeList = employeePage.getRecords();
        return movieMapper.selectPage(new Page<>(currentPage,pageSize),wrapper);
    }

    @Override
    public Movie getAMovieByID(Integer id) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::getId,id)
                .eq(Movie::isDeleted,false);
        return getOne(wrapper);
    }

    @Override
    public Movie getAMovieByImdbID(String imdbId) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::getImdbId,imdbId)
                .eq(Movie::isDeleted,false);
        return getOne(wrapper);
    }

    private boolean isAsc(String order){
        if(order.equals(Sort.ORDER_BY_ASC.getValue())){
            return true;
        } else if(order.equals(Sort.ODER_BY_DESC.getValue())){
            return false;
        }else{
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "错误的顺序标识符");
        }
    }

    @Override
    public Page<Map<String, Object>> getMoviesByIndex(Integer currentPage, Integer pageSize,
                                                      String sort, String order,
                                                      List<String> genres,
                                                      String startYear, String endYear,
                                                      Double ratingFrom, Double ratingTo,
                                                      Integer votesFrom, Integer votesTo,
                                                      List<String> keywords) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Movie::isDeleted,false);

        if(!sort.isBlank()) {
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RATING.getValue()), isAsc(order), Movie::getVoteAverage);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_NUMBER_OF_RATINGS.getValue()), isAsc(order), Movie::getVoteCount);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RELEASE_DATE.getValue()), isAsc(order), Movie::getReleaseDate);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_POPULARITY.getValue()), isAsc(order), Movie::getPopularity);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RUNTIME.getValue()), isAsc(order), Movie::getRuntime);
        }

        if(!genres.isEmpty()){
            for (String i: genres) {
                wrapper.apply("JSON_CONTAINS(JSON_EXTRACT(genres, '$[*].name'), CAST('\""+i+"\"' AS JSON), '$')");
            }
        }

        if (!startYear.isBlank() && !endYear.isBlank()){
            Date startDate = DateTimeUtil.getYearStartDate(startYear);
            Date endDate = DateTimeUtil.getYearEndDate(endYear);
            wrapper.between(Movie::getReleaseDate,startDate,endDate);
        }else if(!startYear.isBlank()){
            Date startDate = DateTimeUtil.getYearStartDate(startYear);
            wrapper.ge(Movie::getReleaseDate,startDate);
        }else if(!endYear.isBlank()){
            Date endDate = DateTimeUtil.getYearEndDate(endYear);
            wrapper.le(Movie::getReleaseDate,endDate);
        }

        if(!Objects.isNull(ratingFrom) && !Objects.isNull(ratingTo)){
            wrapper.between(Movie::getVoteAverage,ratingFrom,ratingTo);
        }else if(!Objects.isNull(ratingFrom)){
            wrapper.ge(Movie::getVoteAverage,ratingFrom);
        }else if(!Objects.isNull(ratingTo)){
            wrapper.le(Movie::getVoteAverage,ratingTo);
        }

        if(!Objects.isNull(votesFrom) && !Objects.isNull(votesTo)){
            wrapper.between(Movie::getVoteCount,votesFrom,votesTo);
        }else if(!Objects.isNull(votesFrom)){
            wrapper.ge(Movie::getVoteCount,votesFrom);
        }else if(!Objects.isNull(votesTo)){
            wrapper.le(Movie::getVoteCount,votesTo);
        }

        //todo: keywords ?
        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
        return movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);
    }
}
