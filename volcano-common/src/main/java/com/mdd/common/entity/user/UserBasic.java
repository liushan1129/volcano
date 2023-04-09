package com.mdd.common.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/3/29 13:27
 */
@Data
public class UserBasic implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色主键
     */
    private String roleIds;

    /**
     * 部门ID
     */
    private String deptIds;

    /**
     * 职位ID
     */
    private String postIds;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

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

    /**
     * 用户类型ID
     */
    private Integer userTypeId;

    /**
     * 课程类型ID
     */
    private Integer courseTypeId;

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
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;

    /**
     * 排序编号
     */
    private Integer sort;

    /**
     * 是否禁用: 0=否, 1=是
     */
    private Integer isDisable;

    /**
     * 是否删除: 0=否, 1=是
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 删除时间
     */
    private Long deleteTime;

}