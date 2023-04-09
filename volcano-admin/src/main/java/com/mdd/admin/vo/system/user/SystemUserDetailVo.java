package com.mdd.admin.vo.system.user;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员详情Vo
 */
@Data
public class SystemUserDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;            // 主键
    private String username;       // 账号
    private String nickname;       // 昵称
    private List<Long> roleIdList; // 角色ID
    private List<Long> deptIdList; // 部门ID
    private List<Long> postIdList; // 岗位ID
    private String avatar;         // 头像

    /**
     * 性别: 0=女, 1=男
     */
    private String gender;

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
