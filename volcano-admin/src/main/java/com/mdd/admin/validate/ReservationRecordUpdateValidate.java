package com.mdd.admin.validate;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 13:55
 */
@Data
public class ReservationRecordUpdateValidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "id不能为空")
    private Long id;
    @NotEmpty(message = "预约日期不能为空")
    private String reservationDate;
    @NotEmpty(message = "预约时间不能为空")
    private String reservationTime;

}
