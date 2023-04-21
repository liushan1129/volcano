package com.mdd.admin.validate;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 12:44
 */
@Data
public class GroupSearchValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String username;
    private String memberName;

}
