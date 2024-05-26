package com.bjtu.movie.service;

import com.bjtu.movie.entity.Watchlist;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 关注清单 服务类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-26
 */
public interface IWatchlistService extends IService<Watchlist> {

    void addToWatchlist(Integer userId, Long movieId);
}
