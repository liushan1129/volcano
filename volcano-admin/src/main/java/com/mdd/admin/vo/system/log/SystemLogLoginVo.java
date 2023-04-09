package com.mdd.admin.vo.system.log;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录日志Vo
 */
@Data
public class SystemLogLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;         // 主键
    private String username;    // 登录账号
    private String ip;          // 来源IP
    private String os;          // 操作系统
    private String browser;     // 浏览器
    private Integer status;     // 操作状态: [1=成功, 2=失败]
    private String createTime;  // 创建时间

}
