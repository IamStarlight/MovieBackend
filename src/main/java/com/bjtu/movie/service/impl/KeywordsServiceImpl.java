package com.bjtu.movie.service.impl;

import com.bjtu.movie.entity.Keywords;
import com.bjtu.movie.dao.KeywordsMapper;
import com.bjtu.movie.service.IKeywordsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-19
 */
@Service
public class KeywordsServiceImpl extends ServiceImpl<KeywordsMapper, Keywords> implements IKeywordsService {

    public List<Keywords> getAllKeywords() {
        return list();
    }
}
