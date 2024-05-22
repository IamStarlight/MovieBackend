package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjtu.movie.entity.Credits;
import com.bjtu.movie.dao.CreditsMapper;
import com.bjtu.movie.service.ICreditsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 演职人员信息 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Service
public class CreditsServiceImpl extends ServiceImpl<CreditsMapper, Credits> implements ICreditsService {

    public List<Credits> getCreditsByMovieId(String id) {
        LambdaQueryWrapper<Credits> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Credits::getId,id);
        return listObjs(wrapper);
    }
}
