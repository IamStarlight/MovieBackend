package com.bjtu.movie.service.impl;

import com.bjtu.movie.entity.MoviesMetadata;
import com.bjtu.movie.dao.MoviesMetadataMapper;
import com.bjtu.movie.service.IMoviesMetadataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 所有影片信息 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
@Service
public class MoviesMetadataServiceImpl extends ServiceImpl<MoviesMetadataMapper, MoviesMetadata> implements IMoviesMetadataService {

}
