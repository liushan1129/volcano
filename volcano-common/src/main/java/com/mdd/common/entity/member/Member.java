package com.mdd.common.entity.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 会员表
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */

@TableName("va_member_basic")
@Data
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("性别: 0=女, 1=男")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("年龄")
    @TableField("age")
    private Integer age;

    @ApiModelProperty("体重")
    @TableField("weight")
    private Integer weight;

    @ApiModelProperty("身高")
    @TableField("height")
    private Integer height;

    @ApiModelProperty("电话号码")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty("课程类型id")
    @TableField("course_type_id")
    private Long courseTypeId;

    @ApiModelProperty("是否结课: 0=否, 1=是")
    @TableField("is_end")
    private Integer isEnd;

    @ApiModelProperty("是否删除: 0=否, 1=是")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty("过期时间")
    @TableField("expire_time")
    private Integer expireTime;

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
