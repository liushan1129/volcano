package com.mdd.admin.dto.member;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.admin.vo.course.CourseTypeVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class MemberDTO implements Serializable {

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
    private CourseTypeDTO courseType;

    //TODO 所属小组信息
//    private GroupDTO groupDTO;
    private Integer isEnd;
    private String isEndStr;

    private Integer isDelete;

    private Boolean isExpired;
    private Integer expireTime;

    private String createTime;

    private String updateTime;

    private String deleteTime;

}
