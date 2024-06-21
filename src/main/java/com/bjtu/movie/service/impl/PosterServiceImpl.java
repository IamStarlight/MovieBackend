package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.entity.Poster;
import com.bjtu.movie.mapper.PosterMapper;
import com.bjtu.movie.service.IPosterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(getById(id) != null){
            return getById(id).getPosterPath();
        }else {
            return "null";
        }
    }
}
