package com.mdd.admin.vo.system.user;

import com.mdd.admin.dto.system.user.SystemUserDetailsDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 23:53
 */
@Data
public class SystemUserSelvesVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private SystemUserDetailsDTO user;        // 用户信息
    private List<String> permissions; // 权限集合: [[*]=>所有权限, ['article:add']=>部分权限]

}
