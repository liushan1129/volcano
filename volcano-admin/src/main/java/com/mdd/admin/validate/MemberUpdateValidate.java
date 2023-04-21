package com.mdd.admin.validate;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 13:55
 */
@Data
public class MemberUpdateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空")
    private Long id;
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    @NotNull(message = "年龄不能为空")
    private Integer age;

    @NotNull(message = "体重不能为空")
    private Integer weight;

    @NotNull(message = "身高不能为空")
    private Integer height;

    @NotEmpty(message = "联系方式不能为空")
    private String telephone;

    @NotNull(message = "课程类型不能为空")
    private Long courseTypeId;

    @NotNull(message = "过期时间不能为空")
    private Integer expireTime;

    private Long groupId;
}
