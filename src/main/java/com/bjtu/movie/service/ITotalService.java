package com.bjtu.movie.service;

import com.bjtu.movie.entity.Total;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-25
 */
public interface ITotalService extends IService<Total> {

    void updateMovieTotalPlus();

    Long getMovieId();
}
