package com.mdd.admin.validate;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:01
 */
@Data
public class CourseTypeCreateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "类型不能为空")
    private Integer category;
    @NotEmpty(message = "名称不能为空")
    private String name;
    @NotNull(message = "课时不能为空")
    private Integer times;


}
