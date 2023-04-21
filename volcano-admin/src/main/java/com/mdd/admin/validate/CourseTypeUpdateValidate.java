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
public class CourseTypeUpdateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空")
    private Long id;
    @NotNull(message = "类型不能为空")
    private Integer category;
    @NotEmpty(message = "名称不能为空")
    private String name;
    @NotNull(message = "课时不能为空")
    private Integer times;
}
