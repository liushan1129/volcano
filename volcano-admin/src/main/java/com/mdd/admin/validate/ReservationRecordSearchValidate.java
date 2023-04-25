package com.mdd.admin.validate;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:44
 */
@Data
public class ReservationRecordSearchValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long groupId;
    private String userName;
    private Long userId;
    private String memberName;
    private Long memberId;
    private String date;
    private String time;


}
