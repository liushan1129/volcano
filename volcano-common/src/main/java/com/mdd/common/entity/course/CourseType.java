package com.mdd.common.entity.course;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.function.LongFunction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 课程类型表
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */

@TableName("va_course_type")
@Data
public class CourseType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("课程类型")
    @TableField("category")
    private Long category;

    @ApiModelProperty("课程名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("课时")
    @TableField("times")
    private Integer times;

    @ApiModelProperty("是否删除: 0=否, 1=是")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Long createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty("删除时间")
    @TableField("delete_time")
    private Long deleteTime;


}
