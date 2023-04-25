package com.mdd.admin;

import com.google.common.collect.Lists;
import com.mdd.admin.dto.reservation.ReservationListDTO;
import com.mdd.admin.dto.reservation.ReservationTimeDTO;
import com.mdd.admin.service.IReservationRecordService;
import com.mdd.admin.validate.ReservationRecordCreateValidate;
import com.mdd.admin.validate.ReservationRecordSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.reservation.ReservationRecord;
import lombok.Data;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/22 13:35
 */
public class ReservationTest extends BaseTest{

    @Resource
    IReservationRecordService reservationRecordService;
    @Test
    public void add() {
        ReservationRecordCreateValidate validate = new ReservationRecordCreateValidate();
        validate.setReservationType(1);
        validate.setGroupId(1L);
        validate.setUserId(8L);
//        validate.setMemberIdList(Lists.newArrayList(1L, 2L));
        validate.setReservationDate("2023-04-22");
        validate.setReservationTime("19:00");
        reservationRecordService.add(validate);
    }

    @Test
    public void list() {

        PageValidate pageValidate = new PageValidate();
        pageValidate.setPageNo(1);
        pageValidate.setPageSize(10);
        ReservationRecordSearchValidate searchValidate = new ReservationRecordSearchValidate();
        searchValidate.setMemberId(1L);
        PageResult<ReservationListDTO> list = reservationRecordService.list(pageValidate, searchValidate);
        System.out.println(list);

    }

    @Test
    public void cancel() {
        reservationRecordService.cancel(1L);
    }


    @Test
    public void test() {
        List<String> enableTimeList = reservationRecordService.getEnableTimeList(8L, "2023-04-22");
        System.out.println(enableTimeList);
    }

}
