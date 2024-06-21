package com.bjtu.movie.service;

import com.bjtu.movie.entity.Genres;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 电影类别表 服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-06-20
 */
public interface IGenresService extends IService<Genres> {

    List<Map<String, Object>> getAllGenres();

    void addAGenres(Genres genres);

    List<Map<String, Object>> getGenresByName(String name);

    List<Map<String, Object>> getGenresById(Integer id);

    void modifyAGenres(Genres genres);

    void deleteGenresById(Integer id);
}
