package com.bjtu.movie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 电影id和imdb/tmdb id之间的映射
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("links")
@ApiModel(value="Links对象", description="电影id和imdb/tmdb id之间的映射")
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电影id")
    private Integer movieId;

    @ApiModelProperty(value = "imdb id")
    private String imdbId;

    @ApiModelProperty(value = "tmdb id")
    private String tmdbId;


}
