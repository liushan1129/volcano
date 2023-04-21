package com.mdd.common.mapper.reservation;

import com.mdd.common.entity.reservation.ReservationRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预约记录表 Mapper 接口
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Mapper
public interface ReservationRecordMapper extends BaseMapper<ReservationRecord> {

}
