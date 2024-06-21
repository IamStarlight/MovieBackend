package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.entity.Poster;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.PosterMapper;
import com.bjtu.movie.service.IPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-06-21
 */
@Service
public class PosterServiceImpl extends ServiceImpl<PosterMapper, Poster> implements IPosterService {

    @Override
    public String getPosterPathById(Integer id) {
        LambdaQueryWrapper<Poster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Poster::getId, id);
        Poster poster = getOne(wrapper);
        if(poster != null){
            return poster.getPosterPath();
        }else {
            return "null";
        }
    }

    @Override
    public void addNewPosterPath(Integer id, String posterPath, String backdropPath) {
        Poster poster = new Poster();
        poster.setId(String.valueOf(id));
        poster.setPosterPath(posterPath);
        poster.setBackdropPath(backdropPath);

        if(!isSamePosterPath(poster)){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "海报已存在");
        }

        save(poster);
    }

    @Override
    public void updateAPoster(Integer id, String posterPath) {
        LambdaQueryWrapper<Poster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Poster::getId, id);
        Poster poster = getOne(wrapper);
        if(poster == null){
            throw new ServiceException(HttpStatus.NOT_FOUND.value(), "海报不存在");
        }
        poster.setPosterPath(posterPath);
        updateById(poster);
    }

    private boolean isSamePosterPath(Poster poster) {
        LambdaQueryWrapper<Poster> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!Objects.isNull(poster.getId()),Poster::getId,poster.getId())
                .eq(!Objects.isNull(poster.getPosterPath()),Poster::getPosterPath,poster.getPosterPath());
        return Objects.isNull(getOne(wrapper));
    }
}
