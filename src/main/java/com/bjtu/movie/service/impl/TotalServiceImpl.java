package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bjtu.movie.entity.Total;
import com.bjtu.movie.mapper.TotalMapper;
import com.bjtu.movie.service.ITotalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-25
 */
@Service
public class TotalServiceImpl extends ServiceImpl<TotalMapper, Total> implements ITotalService {

    @Override
    public void updateMovieTotalPlus() {
        LambdaUpdateWrapper<Total> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Total::getName,"movies_metadata")
                .set(Total::getTotal,getMovieId()+1);
        update(wrapper);
    }

    @Override
    public Integer getMovieId() {
        LambdaQueryWrapper<Total> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Total::getName,"movies_metadata")
                .select(Total::getTotal);
        return getOne(wrapper).getTotal();
    }

    @Override
    public void updateGenresTotalPlus() {
        LambdaUpdateWrapper<Total> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Total::getName,"genres")
                .set(Total::getTotal,getGenresId()+1);
        update(wrapper);
    }

    @Override
    public Integer getGenresId() {
        LambdaQueryWrapper<Total> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Total::getName,"genres")
                .select(Total::getTotal);
        return getOne(wrapper).getTotal();
    }
}
