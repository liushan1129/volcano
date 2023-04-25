package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.reservation.ReservationDTO;
import com.mdd.admin.dto.reservation.ReservationListDTO;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.ReservationRecordSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.ReservationCancelCondition;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.entity.reservation.ReservationRecord;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.enums.ReservationStatusEnum;
import com.mdd.common.mapper.course.CourseTypeMapper;
import com.mdd.common.mapper.reservation.ReservationRecordMapper;
import com.mdd.common.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liushan
 * @data 2023/4/17 12:11
 */
@Component
public class ReservationRepo {

    @Resource
    ReservationRecordMapper reservationRecordMapper;

    public ReservationRecord queryByTypeAndName(Integer type, String name) {
        QueryWrapper wrapper = new QueryWrapper<CourseType>()
//                .eq("type", type)
                .eq("name", name);
        return  reservationRecordMapper.selectOne(wrapper);
    }

    public IPage<ReservationListDTO> queryList(PageValidate pageValidate, ReservationRecordSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<ReservationRecord> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.group_id, t.user_id, t.member_id, t.status, t.reservation_date, " +
                        "t.reservation_time, t.create_time, t.update_time")
                .apply(searchValidate.getMemberId() != null, "FIND_IN_SET ('"+searchValidate.getMemberId()+"', member_id)")
                .orderByAsc(Arrays.asList("t.reservation_date","t.reservation_time"));

        reservationRecordMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "=:groupId@group_id:long",
                "=:userId@user_id:int",
                "=:memberId@member_id:int",
                "=:date@reservation_date:str",
                "=:time@reservation_time:str"
        });

        IPage<ReservationListDTO> iPage = reservationRecordMapper.selectJoinPage(
                new Page<>(page, limit),
                ReservationListDTO.class,
                mpjQueryWrapper);

        return iPage;
    }

    public ReservationRecord queryById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper<ReservationRecord>();
        queryWrapper.eq("id", id);
        return reservationRecordMapper.selectOne(queryWrapper);
    }

    public List<ReservationRecord> queryByUserIdAndDateTime(Long userId, String reservationDate, String reservationTime) {
        QueryWrapper queryWrapper = new QueryWrapper<ReservationRecord>()
                .ne("status", ReservationStatusEnum.FINISH.getCode());
        if(userId != null) {
            queryWrapper.eq("user_id", userId);
        }
        if(StringUtils.isNotBlank(reservationDate)) {
            queryWrapper.eq("reservation_date", reservationDate);
        }
        if(StringUtils.isNotBlank(reservationTime)) {
            queryWrapper .eq("reservation_time", reservationTime);
        }

        return reservationRecordMapper.selectList(queryWrapper);
    }

    public List<ReservationRecord> queryByGroupIdAndDateTime(Long groupId, String reservationDate, String reservationTime) {
        QueryWrapper queryWrapper = new QueryWrapper<ReservationRecord>()
                .eq("group_id", groupId)
                .eq("reservation_date", reservationDate)
                .eq("reservation_time", reservationTime)
                .eq("status", ReservationStatusEnum.NOT_START.getCode());
        return reservationRecordMapper.selectList(queryWrapper);
    }

    public void batchCancel(List<ReservationRecord> reservationRecords, long cancelTime) {
        reservationRecordMapper.batchCancel(reservationRecords.stream().map(ReservationRecord::getId).collect(Collectors.toList()), cancelTime);
    }
}
