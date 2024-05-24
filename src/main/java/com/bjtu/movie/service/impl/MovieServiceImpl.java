package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.constants.Sort;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.dao.MovieMapper;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.model.MovieCalender;
import com.bjtu.movie.service.IMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Page<Map<String,Object>> getAllMovies(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::isDeleted,false)
                .select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
        return movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);
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

        if (!keywords.isEmpty()){
            for (String i: keywords) {
                wrapper.inSql(Movie::getId,
                        "select id from keywords "
                            + "where JSON_CONTAINS(JSON_EXTRACT(keywords, '$[*].name'), CAST('\""+i+"\"' AS JSON), '$')");
            }
        }

        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
        return movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);
    }

    @Override
    public void addNewMovie(Movie movie) {
        //todo: 重复处理
        movie.setVoteCount(0);
        movie.setVoteAverage(0.0);
        movie.setDeleted(false);
        //todo: id有问题
        save(movie);
    }

    @Override
    public void updateAMovieInfo(Movie movie) {
        if(getAMovieByID(movie.getId()) == null){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影不存在");
        }
        updateById(movie);
    }

    @Override
    public void deleteAMovie(Integer id) {
        Movie movie = getAMovieByID(id);
        if(movie == null){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影不存在");
        }
        movie.setDeleted(true);
        updateById(movie);
    }

    @Override
    public List<Map<String, Object>> getTopNMovie() {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime)
                .orderByDesc(Movie::getVoteAverage)
                .last("LIMIT 100");
        return movieMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getMostPopularNMovie() {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime)
                .orderByDesc(Movie::getVoteAverage)
                .last("LIMIT 250");
        return movieMapper.selectMaps(wrapper);
    }

    @Override
    public Page<MovieCalender> getMovieGroupByDate(Integer currentPage, Integer pageSize) {
        Page<MovieCalender> movieCalenderPage = movieMapper.getMovieGroupByDate(new Page<>(currentPage,pageSize));
        for(MovieCalender i: movieCalenderPage.getRecords()){
            List<Map<String,Object>> movieList = new ArrayList<>();
            List<Integer> idList = new ArrayList<>();
            String[] stringIds = i.getIds().split(",");
            for (String id: stringIds){
                idList.add(Integer.valueOf(id));
            }
            for (Integer j: idList){
                LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Movie::getId,j)
                        .select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
                movieList.add(getMap(wrapper));
            }
            i.setMovieList(movieList);
        }
        return movieCalenderPage;
    }
}
