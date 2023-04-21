package com.mdd.admin.validate;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 12:01
 */
@Data
public class GroupCreateValidate implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "小组名称不能为空")
    private String name;

    @NotNull(message = "教练不能为空")
    private Long userId;

    private String username;

    @NotEmpty(message = "学员不能为空")
    private List<Long> memberIds;

    private List<String> memberNames;


}
