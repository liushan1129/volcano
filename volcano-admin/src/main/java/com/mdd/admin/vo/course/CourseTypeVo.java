package com.mdd.admin.vo.course;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 13:37
 */
@Data
public class CourseTypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long category;

    private String categoryName;

    private String name;

    private Integer times;

    private String createTime;
}
