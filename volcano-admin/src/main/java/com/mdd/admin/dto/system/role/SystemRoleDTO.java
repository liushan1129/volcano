package com.mdd.admin.dto.system.role;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/4 17:30
 */
@Data
public class SystemRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;         // 主键
    private String name;        // 角色名称
    private String remark;      // 角色备注
    private Object menuList;       // 关联菜单
    private Integer member;     // 成员数量
    private Integer sort;       // 角色排序
    private Integer isDisable;  // 是否禁用: [0=否, 1=是]
    private String createTime;  // 创建时间
    private String updateTime;  // 更新时间
}
