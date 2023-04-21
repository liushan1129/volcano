package com.mdd.admin.dto.group;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class GroupDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long userId;
    private String userName;

    private String memberIds;
    private String memberNames;

    private String createTime;
    private String updateTime;


}
