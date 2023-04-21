package com.mdd.admin.dto.member;

import com.mdd.admin.dto.group.GroupDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 22:23
 */
@Data
public class MemberDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Integer gender;

    private Integer age;

    private Integer weight;

    private Integer height;

    private String telephone;

    private Long courseTypeId;
    private Long courseCategory;

    private String courseCategoryName;

    //TODO 所属小组信息
//    private GroupDTO groupDTO;

    private Integer isEnd;

    private Integer isDelete;

    private Integer expireTime;

    private String createTime;

    private String updateTime;

    private String deleteTime;

}

