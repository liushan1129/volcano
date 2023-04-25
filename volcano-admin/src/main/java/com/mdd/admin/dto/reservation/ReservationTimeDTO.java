package com.mdd.admin.dto.reservation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/23 19:31
 */
@Data
public class ReservationTimeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String time;
}
