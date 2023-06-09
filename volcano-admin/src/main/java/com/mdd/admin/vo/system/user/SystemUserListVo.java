package com.mdd.admin.vo.system.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/1 17:07
 */
@Data
public class SystemUserListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;            // 主键
    private String username;       // 账号
    private String nickname;       // 昵称
    private String deptName;       // 部门
    private String roleName;       // 角色

    /**
     * 性别: 0=女, 1=男
     */
    private Integer gender;

    private String genderStr;
    // 多端登录: [0=否, 1=是]
    private Integer isMultipoint;
    private Integer isDisable;     // 是否禁用: [0=否, 1=是]
    private String lastLoginIp;    // 最后登录IP
    private String lastLoginTime;  // 最后登录时间
    private String createTime;     // 创建时间
    private String updateTime;     // 更新时间
}
