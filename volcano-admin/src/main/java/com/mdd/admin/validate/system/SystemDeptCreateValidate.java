package com.mdd.admin.validate.system;

import com.mdd.common.validator.annotation.IntegerContains;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 系统部门创建参数
 */
@Data
public class SystemDeptCreateValidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "pid参数缺失")
    @DecimalMin(value = "0", message = "上级值不能少于0")
    private Integer pid;

    @NotNull(message = "name参数缺失")
    @Length(min = 1, max = 100, message = "部门名称必须在1~100个字符内")
    private String name;

    @Length(min = 1, max = 30, message = "负责人名称必须在1~30个字符内")
    private String duty = "";

    @Length(min = 11, max = 11, message = "手机号只能为11位")
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$", message = "手机号格式有误")
    private String mobile;

    @NotNull(message = "请选择状态")
    @IntegerContains(values = {0, 1})
    private Integer isStop;

    @NotNull(message = "排序号不能为空")
    @DecimalMin(value = "0", message = "排序号值不能少于0")
    @DecimalMax(value = "9999", message = "排序号值不能大于9999")
    private Integer sort;

}
