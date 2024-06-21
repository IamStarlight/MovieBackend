package com.bjtu.movie.service;

import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.entity.Poster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-06-21
 */
public interface IPosterService extends IService<Poster> {

    String getPosterPathById(Integer id);

    void addNewPosterPath(Integer id, String posterPath, String backdropPath);

    void updateAPoster(Integer id, String posterPath);

}
