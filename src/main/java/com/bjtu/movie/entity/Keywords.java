package com.bjtu.movie.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import com.bjtu.movie.handler.JsonListTypeHandler;
import com.bjtu.movie.model.Info;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 所有电影的演职人员信息
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "keywords",autoResultMap = true)
@ApiModel(value="Keywords对象", description="所有电影的演职人员信息")
public class Keywords implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电影id")
    private Integer id;

    @ApiModelProperty(value = "演职人员id和name")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Info> keywords;
}
