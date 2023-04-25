package com.mdd.admin.validate;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 12:01
 */
@Data
public class ReservationRecordCreateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    //预约形式
    // 1 小组， 2 单人
    @NotNull(message = "预约形式不能为空")
    private Integer reservationType;
    private Long groupId;
    @NotNull(message = "用户不能为空")
    private Long userId;
    @NotEmpty(message = "会员不能为空")
    private String memberId;
    @NotEmpty(message = "预约日期不能为空")
    private String reservationDate;
    @NotEmpty(message = "预约时间不能为空")
    private String reservationTime;

}
