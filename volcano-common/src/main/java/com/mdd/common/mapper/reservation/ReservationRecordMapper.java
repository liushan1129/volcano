package com.mdd.common.mapper.reservation;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.reservation.ReservationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预约记录表 Mapper 接口
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Mapper
public interface ReservationRecordMapper extends IBaseMapper<ReservationRecord> {

    void batchInsert(@Param("list") List<ReservationRecord> reservationRecords);

    void batchCancel(@Param("ids") List<Long> reservationRecords, @Param("cancelTime") Long cancelTime);
}
