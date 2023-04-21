package com.mdd.admin.vo.member;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 13:37
 */
@Data
public class MemberDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private String name;

    private Integer gender;

    private Integer age;

    private Integer weight;

    private Integer height;

    private String telephone;

    private Long courseCategory;

    private Long courseTypeId;

//    private String courseTypeName;

    private Integer isEnd;

    private Integer isDelete;

    private Integer expireTime;

    private String createTime;

    private String updateTime;

    private String deleteTime;
}
