package com.mdd.admin.dto.course;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class CourseTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer category;

    private String categoryName;

    private String name;

    private Integer times;

    private String createTime;


}
