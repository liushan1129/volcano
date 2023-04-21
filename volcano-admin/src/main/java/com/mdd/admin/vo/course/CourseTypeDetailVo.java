package com.mdd.admin.vo.course;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 13:40
 */
@Data
public class CourseTypeDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long category;

    private String categoryName;

    private String name;

    private Integer times;

    private String createTime;     // 创建时间
    private String updateTime;     // 更新时间
}
