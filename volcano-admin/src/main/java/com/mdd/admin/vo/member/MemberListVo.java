package com.mdd.admin.vo.member;

import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.vo.course.CourseTypeVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 13:37
 */
@Data
public class MemberListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer gender;
    private String genderStr;

    private Integer age;

    private Integer weight;

    private Integer height;

    private String telephone;

    private Long courseCategory;
    private String courseCategoryName;
    private Long courseTypeId;
    private CourseTypeVo courseType;

    private Integer isEnd;
    private String isEndStr;

    private Integer isDelete;

    private Integer expireTime;

    private String createTime;



}
