package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.entity.Watchlist;
import com.bjtu.movie.mapper.WatchlistMapper;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.service.IWatchlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 关注清单 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-26
 */
@Service
public class WatchlistServiceImpl extends ServiceImpl<WatchlistMapper, Watchlist> implements IWatchlistService {

    @Autowired
    private WatchlistMapper watchlistMapper;

    @Override
    public void addToWatchlist(Integer userId, Long movieId) {
        LambdaQueryWrapper<Watchlist> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Watchlist::getUserId,userId)
                .eq(Watchlist::getMovieId,movieId)
                .last("LIMIT 1");
        if(!listObjs(wrapper).isEmpty()){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影已在列表中");
        }else{
            Watchlist watchlist = new Watchlist();
            watchlist.setMovieId(movieId);
            watchlist.setUserId(userId);
            watchlist.setTimestamp(DateTimeUtil.createNowTimeStamp());
            save(watchlist);
        }
    }

    public List<Map<String,Object>> getWatchlist(Integer userId) {
        return watchlistMapper.getWatchlist(userId);
    }
}
