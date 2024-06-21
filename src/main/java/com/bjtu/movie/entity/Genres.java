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
 * 电影类别表
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("genres")
@ApiModel(value="Genres对象", description="电影类别表")
public class Genres implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别id")
    private Integer id;

    @ApiModelProperty(value = "类别名称")
    private String name;

    @ApiModelProperty(value = "是否删除")
    private boolean deleted;

}
