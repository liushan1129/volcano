package com.mdd.admin.dto.system.dept;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/4 17:01
 */
@Data
public class SystemDeptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;        // 主键
    private Long pid;       // 部门父级
    private String name;       // 部门名称
    private String duty;       // 负责人
    private String mobile;     // 联系电话
    private Integer sort;      // 排序编号
    private Integer isStop;    // 是否停用: [0=否, 1=是]
    private String createTime; // 创建时间
    private String updateTime; // 更新时间
}
