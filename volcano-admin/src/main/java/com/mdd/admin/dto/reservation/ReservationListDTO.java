package com.mdd.admin.dto.reservation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class ReservationListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long groupId;
    private String groupName;

    private Long userId;

    private String userName;


    private String memberId;
    private String memberNames;

    private String reservationDate;
    private String reservationTime;
    private String reservationDateTime;

    private Integer status;
    private String statusDesc;


}
