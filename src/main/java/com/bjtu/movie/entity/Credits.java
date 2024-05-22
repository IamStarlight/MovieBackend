package com.bjtu.movie.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.bjtu.movie.handler.JsonListTypeHandler;
import com.bjtu.movie.model.Cast;
import com.bjtu.movie.model.Crew;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 演职人员信息
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "credits",autoResultMap = true)
@ApiModel(value="Credits对象", description="演职人员信息")
public class Credits implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "演员信息")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Cast> cast;

    @ApiModelProperty(value = "工作人员信息")
    @TableField(typeHandler = JsonListTypeHandler.class)
    private List<Crew> crew;

    @ApiModelProperty(value = "电影id")
    private Integer id;


}
