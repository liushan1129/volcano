package com.mdd.admin.dto.group;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/19 22:22
 */
@Data
public class GroupDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long userId;
    private String username;

    private List<Long> memberIdList;
    private List<String> memberNameList;

    private String createTime;
    private String updateTime;
}
