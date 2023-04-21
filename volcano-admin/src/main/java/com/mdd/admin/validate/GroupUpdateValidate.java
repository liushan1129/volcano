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
public class GroupUpdateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空")
    private Long id;

    @NotEmpty(message = "小组名称不能为空")
    private String name;

    @NotNull(message = "教练不能为空")
    private Long userId;

    @NotEmpty(message = "学员不能为空")
    private String memberIds;

}
