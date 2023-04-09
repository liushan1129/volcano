package com.mdd.admin.dto.system.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/3/31 23:18
 */
@Data
public class SystemUserBasicListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 部门
     */
    private String deptIds;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 角色
     */
    private String roleIds;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 性别: 0=未知, 1=男， 2=女
     */
    private Integer gender;

    private String genderStr;

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
    /**
     * 用户密码
     */
    private String password;

    /**
     * 加密盐巴
     */
    private String salt;

    /**
     * 多端登录: 0=否, 1=是
     */
    private Integer isMultipoint;
    /**
     * 是否禁用: 0=否, 1=是
     */
    private Integer isDisable;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

    public void setGenderStr(Integer gender) {
        switch (gender) {
            case 0:
                this.genderStr = "未知";
                break;
            case 1:
                this.genderStr = "男";
                break;
            case 2:
                this.genderStr = "女";
                break;
        }
    }
}
