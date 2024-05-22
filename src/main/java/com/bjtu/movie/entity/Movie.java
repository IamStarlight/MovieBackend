package com.bjtu.movie.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bjtu.movie.handler.JsonListTypeHandler;
import com.bjtu.movie.model.Collection;
import com.bjtu.movie.model.Country;
import com.bjtu.movie.model.Info;
import com.bjtu.movie.model.Language;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName(value = "movies_metadata",autoResultMap = true)
@ApiModel(value="MoviesMetadata对象", description="所有影片信息")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否是成人电影")
    private String adult;

    @ApiModelProperty(value = "属于哪个系列")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private Collection belongsToCollection;

    @ApiModelProperty(value = "预算")
    private Integer budget;

    @ApiModelProperty(value = "流派")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Info> genres;

    @ApiModelProperty(value = "主页")
    private String homepage;

    @ApiModelProperty(value = "电影id")
    private Integer id;

    @ApiModelProperty(value = "imdb id")
    private String imdbId;

    @ApiModelProperty(value = "电影原始语言")
    private String originalLanguage;

    @ApiModelProperty(value = "电影原始标题")
    private String originalTitle;

    @ApiModelProperty(value = "概述")
    private String overview;

    @ApiModelProperty(value = "人气")
    private Double popularity;

    @ApiModelProperty(value = "海报图像路径")
    private String posterPath;

    @ApiModelProperty(value = "出版公司")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Info> productionCompanies;

    @ApiModelProperty(value = "出版国家")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Country> productionCountries;

    @ApiModelProperty(value = "上映时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @ApiModelProperty(value = "收入")
    private Integer revenue;

    @ApiModelProperty(value = "时长")
    private Integer runtime;

    @ApiModelProperty(value = "使用的语言")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Language> spokenLanguages;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "宣传标语")
    private String tagline;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "是否有视频")
    private String video;

    @ApiModelProperty(value = "平均评分")
    private Double voteAverage;

    @ApiModelProperty(value = "评分人数")
    private Integer voteCount;

    @ApiModelProperty(value = "是否删除")
    private boolean deleted;

//    @TableField(value = "genres ->> '$[*].name'",
//            insertStrategy = FieldStrategy.NEVER,
//            updateStrategy = FieldStrategy.NEVER,
//            select = false)
//    private String name;

//    @TableField(value = "select * from keywords where id = ",
//            insertStrategy = FieldStrategy.NEVER,
//            updateStrategy = FieldStrategy.NEVER,
//            select = false)

//    @JoinTableField(table = "order", column = "orderName", joinColumn = "id", foreignKey = "userId")
//    private Keywords keywords;
}
