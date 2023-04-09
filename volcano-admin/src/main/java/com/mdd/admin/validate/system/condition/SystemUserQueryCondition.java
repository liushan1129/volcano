package com.mdd.admin.validate.system.condition;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/3 10:11
 */
@Data
public class SystemUserQueryCondition {
    private Long id;
    private String username;
    private String nickname;
}
