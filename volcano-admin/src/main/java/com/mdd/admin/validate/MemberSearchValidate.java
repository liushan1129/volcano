package com.mdd.admin.validate;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:44
 */
@Data
public class MemberSearchValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    private Integer gender;

    private Integer age;

    private String telephone;

    private Long courseTypeId;

    private Integer isEnd;

}
