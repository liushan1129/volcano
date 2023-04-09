package com.mdd.admin.vo.user;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/3/29 13:54
 */
public class UserListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;            // 主键
    private String username;       // 账号
    private String enUsername;     //英文名
    private String nickname;       // 昵称
    private String dept;           // 部门
    private String role;           // 角色
    /**
     * 性别: 0=女, 1=男
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 体重
     */
    private Integer weight;

    /**
     * 身高
     */
    private Integer height;


    // 多端登录: [0=否, 1=是]
    private Integer isMultipoint;
    private Integer isDisable;     // 是否禁用: [0=否, 1=是]
    private String lastLoginIp;    // 最后登录IP
    private String lastLoginTime;  // 最后登录时间
    private String createTime;     // 创建时间
    private String updateTime;     // 更新时间

}

