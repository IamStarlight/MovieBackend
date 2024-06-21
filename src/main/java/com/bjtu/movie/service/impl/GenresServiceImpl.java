package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bjtu.movie.entity.Genres;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.mapper.GenresMapper;
import com.bjtu.movie.service.IGenresService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 电影类别表 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-06-20
 */
@Service
public class GenresServiceImpl extends ServiceImpl<GenresMapper, Genres> implements IGenresService {

    @Autowired
    private GenresMapper genresMapper;

    @Autowired
    private TotalServiceImpl totalService;

    @Override
    public List<Map<String, Object>> getAllGenres() {
        LambdaQueryWrapper<Genres> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Genres::isDeleted,false)
                .select(Genres::getId,Genres::getName);
        return genresMapper.selectMaps(wrapper);
    }

    @Override
    public void addAGenres(Genres genres) {
        if(Objects.isNull(getGenresByName(genres.getName()))){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "类别已存在");
        }
        totalService.updateGenresTotalPlus();
        genres.setId(totalService.getGenresId());
        genres.setDeleted(false);
        save(genres);
    }

    @Override
    public List<Map<String, Object>> getGenresByName(String name) {
        LambdaQueryWrapper<Genres> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Genres::getName,name)
                .eq(Genres::isDeleted,false)
                .select(Genres::getId,Genres::getName);
        return genresMapper.selectMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getGenresById(Integer id) {
        LambdaQueryWrapper<Genres> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Genres::getId,id)
                .eq(Genres::isDeleted,false)
                .select(Genres::getId,Genres::getName);
        return genresMapper.selectMaps(wrapper);
    }

    @Override
    public void modifyAGenres(Genres genres) {
        if(getGenresById(genres.getId()) == null) {
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "类别不存在");
        }
        LambdaUpdateWrapper<Genres> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Genres::getId,genres.getId())
                .set(Genres::getName,genres.getName());
        update(wrapper);
    }

    @Override
    public void deleteGenresById(Integer id) {
        if(getGenresById(id) == null){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "类别不存在");
        }
        LambdaUpdateWrapper<Genres> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Genres::getId,id)
                .set(Genres::isDeleted,true);
        update(wrapper);
    }
}
