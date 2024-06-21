package com.bjtu.movie.service;

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
}
