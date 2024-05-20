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
 * 所有影片信息
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("movies_metadata")
@ApiModel(value="MoviesMetadata对象", description="所有影片信息")
public class MoviesMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否是成人电影")
    @TableField("adult")
    private String adult;

    @ApiModelProperty(value = "属于哪个系列")
    @TableField("belongs_to_collection")
    private String belongsToCollection;

    @ApiModelProperty(value = "预算")
    @TableField("budget")
    private Integer budget;

    @ApiModelProperty(value = "流派")
    @TableField("genres")
    private String genres;

    @ApiModelProperty(value = "主页")
    @TableField("homepage")
    private String homepage;

    @ApiModelProperty(value = "电影id")
    @TableField("id")
    private Integer id;

    @ApiModelProperty(value = "imdb id")
    @TableField("imdb_id")
    private String imdbId;

    @ApiModelProperty(value = "电影yuan语言")
    @TableField("original_language")
    private String originalLanguage;

    @ApiModelProperty(value = "电影原始标题")
    @TableField("original_title")
    private String originalTitle;

    @ApiModelProperty(value = "概述")
    @TableField("overview")
    private String overview;

    @ApiModelProperty(value = "人气")
    @TableField("popularity")
    private Double popularity;

    @ApiModelProperty(value = "海报图像路径")
    @TableField("poster_path")
    private String posterPath;

    @ApiModelProperty(value = "出版公司")
    @TableField("production_companies")
    private String productionCompanies;

    @ApiModelProperty(value = "出版国家")
    @TableField("production_countries")
    private String productionCountries;

    @ApiModelProperty(value = "上映时间")
    @TableField("release_date")
    private String releaseDate;

    @ApiModelProperty(value = "收入")
    @TableField("revenue")
    private Integer revenue;

    @ApiModelProperty(value = "时长")
    @TableField("runtime")
    private Integer runtime;

    @ApiModelProperty(value = "使用的语言")
    @TableField("spoken_languages")
    private String spokenLanguages;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "宣传标语")
    @TableField("tagline")
    private String tagline;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "是否有视频")
    @TableField("video")
    private String video;

    @ApiModelProperty(value = "平均评分")
    @TableField("vote_average")
    private Double voteAverage;

    @ApiModelProperty(value = "评分人数")
    @TableField("vote_count")
    private Integer voteCount;


}
