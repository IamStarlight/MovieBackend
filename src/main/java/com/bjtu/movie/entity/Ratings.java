package com.bjtu.movie.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户评分表
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ratings")
@ApiModel(value="Ratings对象", description="用户评分表")
public class Ratings implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "电影id")
    private Integer movieId;

    @ApiModelProperty(value = "评分")
    private Double rating;

    @ApiModelProperty(value = "时间戳")
    private Long timestamp;

    @TableField(value = "avg(rating)",
            insertStrategy = FieldStrategy.NEVER,
            updateStrategy = FieldStrategy.NEVER,
            select = false)
    private Double avg;

}
