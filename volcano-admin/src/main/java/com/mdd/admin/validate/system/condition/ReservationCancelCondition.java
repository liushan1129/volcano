package com.mdd.admin.validate.system.condition;

import lombok.Data;

/**
 * @author liushan
 * @data 2023/4/22 19:34
 */
@Data
public class ReservationCancelCondition {
    private Long id;
    private Long groupId;
    private String date;
    private String time;
}
