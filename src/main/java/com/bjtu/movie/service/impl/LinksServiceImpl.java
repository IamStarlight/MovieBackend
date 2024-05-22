package com.bjtu.movie.service.impl;

import com.bjtu.movie.entity.Links;
import com.bjtu.movie.dao.LinksMapper;
import com.bjtu.movie.service.ILinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 电影id和imdb/tmdb id之间的映射 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Service
public class LinksServiceImpl extends ServiceImpl<LinksMapper, Links> implements ILinksService {

}
