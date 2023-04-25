package com.mdd.admin.service;

import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.dto.reservation.ReservationListDTO;
import com.mdd.admin.dto.reservation.ReservationTimeDTO;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.reservation.ReservationRecordListVo;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.reservation.ReservationRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 预约记录表 服务类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
public interface IReservationRecordService extends IService<ReservationRecord> {
    void add(ReservationRecordCreateValidate createValidate);
    PageResult<ReservationListDTO> list(PageValidate pageValidate, ReservationRecordSearchValidate searchValidate);
    Object detail(Long id);
    void edit(ReservationRecordUpdateValidate updateValidate);
    void cancel(Long longValue);

    List<String> getEnableTimeList(Long userId, String reservationDate);
}
