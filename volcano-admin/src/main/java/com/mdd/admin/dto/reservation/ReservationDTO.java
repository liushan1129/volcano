package com.mdd.admin.dto.reservation;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private String typeName;

    private String name;

    private Integer times;

    private String createTime;


}
