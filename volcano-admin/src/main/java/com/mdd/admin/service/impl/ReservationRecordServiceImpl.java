package com.mdd.admin.service.impl;

import com.mdd.admin.service.IReservationRecordService;
import com.mdd.common.entity.reservation.ReservationRecord;
import com.mdd.common.mapper.reservation.ReservationRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预约记录表 服务实现类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Service
public class ReservationRecordServiceImpl extends ServiceImpl<ReservationRecordMapper, ReservationRecord> implements IReservationRecordService {

}
