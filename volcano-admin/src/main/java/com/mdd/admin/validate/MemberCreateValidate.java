package com.mdd.admin.validate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.common.entity.group.Group;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:01
 */
@Data
public class MemberCreateValidate implements Serializable {
    private static final long serialVersionUID = 1L;

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

    //所属小组
    private Long groupId;

}
