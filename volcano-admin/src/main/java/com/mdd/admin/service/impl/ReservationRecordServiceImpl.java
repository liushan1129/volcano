package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.dto.member.MemberDetailDTO;
import com.mdd.admin.dto.reservation.ReservationDTO;
import com.mdd.admin.dto.reservation.ReservationListDTO;
import com.mdd.admin.dto.reservation.ReservationRecordDetailDTO;
import com.mdd.admin.dto.reservation.ReservationTimeDTO;
import com.mdd.admin.repo.GroupRepo;
import com.mdd.admin.repo.MemberRepo;
import com.mdd.admin.repo.ReservationRepo;
import com.mdd.admin.repo.SystemUserRepo;
import com.mdd.admin.service.IReservationRecordService;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.validate.ReservationRecordCreateValidate;
import com.mdd.admin.validate.ReservationRecordSearchValidate;
import com.mdd.admin.validate.ReservationRecordUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.ReservationCancelCondition;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.group.Group;
import com.mdd.common.entity.member.Member;
import com.mdd.common.entity.reservation.ReservationRecord;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.enums.CourseCategoryEnum;
import com.mdd.common.enums.ReservationStatusEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.reservation.ReservationRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.EnumUtils;
import com.mdd.common.util.StringUtils;
import com.mdd.common.util.TimeUtils;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private ReservationRepo reservationRepo;
    @Resource
    private ReservationRecordMapper reservationRecordMapper;
    @Resource
    private SystemUserRepo userRepo;
    @Resource
    private MemberRepo memberRepo;

    @Resource
    private GroupRepo groupRepo;

    @Override
    public void add(ReservationRecordCreateValidate createValidate) {
        if(createValidate.getReservationType() == 1 && createValidate.getGroupId() == null) {
            throw new OperateException("请选择预约小组");
        }
        if(createValidate.getReservationType() == 1) {
            Group group = groupRepo.queryById(createValidate.getGroupId());
            if (group == null) {
                throw new OperateException("小组不存在");
            }
        }
        UserBasic userBasic = userRepo.queryById(createValidate.getUserId());
        List<ReservationRecord> existReservationRecords = reservationRepo.queryByUserIdAndDateTime(createValidate.getUserId(),
                createValidate.getReservationDate(), createValidate.getReservationTime());
        if(CollectionUtils.isNotEmpty(existReservationRecords)) {
            throw new OperateException(userBasic.getUsername() + " 该时间已被预约");

        }
        ReservationRecord reservationRecord = DozerUtils.map(createValidate, ReservationRecord.class);
        reservationRecordMapper.insert(reservationRecord);

    }

@Override
public PageResult<ReservationListDTO> list(PageValidate pageValidate, ReservationRecordSearchValidate searchValidate) {
    if(StringUtils.isNotBlank(searchValidate.getUserName())) {
        UserBasic userBasic = userRepo.queryByName(searchValidate.getUserName());
        if(userBasic == null) {
            return  null;
        }
        searchValidate.setUserId(userBasic.getId());
    }

    if(StringUtils.isNotBlank(searchValidate.getMemberName())) {
        Member member = memberRepo.queryByName(searchValidate.getMemberName());
        if(member == null) {
            return PageResult.iPageHandle(null);
        }
        searchValidate.setMemberId(member.getId());
    }
    IPage<ReservationListDTO> iPage = reservationRepo.queryList(pageValidate, searchValidate);
    List<UserBasic> userBasics = userRepo.all();
    Map<Long, UserBasic> userBasicMap = userBasics.stream().collect(Collectors.toMap(UserBasic :: getId, u -> u));
    List<Member> members = memberRepo.all();
    Map<Long, Member> memberMap = members.stream().collect(Collectors.toMap(Member :: getId, m -> m));
    List<Group> groups = groupRepo.queryByCondition(null);
    Map<Long, Group> groupMap = groups.stream().collect(Collectors.toMap(Group::getId, g -> g));
    iPage.getRecords().stream().forEach(dto -> {
        dto.setGroupName(groupMap.get(dto.getGroupId()) == null ? null : groupMap.get(dto.getGroupId()).getName());
        dto.setUserName(userBasicMap.get(dto.getUserId()) == null ? null : userBasicMap.get(dto.getUserId()).getUsername());
        List<Long> memberIdList = Arrays.asList(dto.getMemberId().split(",")).stream().map(mid -> Long.valueOf(mid.trim())).collect(Collectors.toList());
        StringBuffer memberNameBuf = new StringBuffer();
        memberIdList.forEach(memberId -> {
            memberNameBuf.append(memberMap.get(memberId) == null ? null : memberMap.get(memberId).getName());
            memberNameBuf.append(",");
        });
        dto.setMemberNames(memberNameBuf.toString().substring(0, memberNameBuf.length() - 1));
        dto.setStatusDesc(ReservationStatusEnum.valueOfCode(dto.getStatus()).getMsg());
        dto.setReservationDateTime(dto.getReservationDate() + " " + dto.getReservationTime());
    });
    return PageResult.iPageHandle(iPage);
}

    @Override
    public ReservationRecordDetailDTO detail(Long id) {
        ReservationRecord reservationRecord = reservationRepo.queryById(id);
        ReservationRecordDetailDTO dto = DozerUtils.map(reservationRecord, ReservationRecordDetailDTO.class);

        UserBasic userBasic = userRepo.queryById(reservationRecord.getUserId());
              dto.setMemberId(reservationRecord.getMemberId());

        if(reservationRecord.getGroupId() != null) {
            Group group = groupRepo.queryById(reservationRecord.getGroupId());
            dto.setGroupName(group.getName());
            dto.setMemberNames(group.getMemberNames());
        }  else  {
            List<Long> memberIds = Arrays.asList(reservationRecord.getMemberId().split(",")).stream().map(s -> Long.valueOf(s)).collect(Collectors.toList());
            List<Member> members = memberRepo.queryByIds(memberIds);
            String memberNameStr = members.stream().map(Member::getName).collect(Collectors.toList()).toString();
            dto.setMemberNames(memberNameStr.substring(1, memberNameStr.length() - 1));
        }
        //2 个人 ；1 小组
        dto.setReservationType(dto.getGroupId() == null ? 2L : 1L);
        dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
        dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
        dto.setCancelTime(dto.getCancelTime() == null ? null : TimeUtils.timestampToDate(dto.getCancelTime()));
        dto.setUsername(userBasic == null ? null : userBasic.getUsername());
        dto.setStatusDesc(ReservationStatusEnum.valueOfCode(dto.getStatus()).getMsg());
        return dto;
    }

    @Override
    public void edit(ReservationRecordUpdateValidate updateValidate) {
        ReservationRecord reservationRecord = reservationRepo.queryById(updateValidate.getId());
        if(reservationRecord == null) {
            throw new OperateException("预约记录不存在");
        }
        UserBasic userBasic = userRepo.queryById(reservationRecord.getUserId());
        List<ReservationRecord> existReservationRecord = reservationRepo.queryByUserIdAndDateTime(reservationRecord.getUserId(),
                updateValidate.getReservationDate(), updateValidate.getReservationTime());
        if(CollectionUtils.isNotEmpty(existReservationRecord) && existReservationRecord.get(0).getId() != updateValidate.getId()) {
            throw new OperateException(userBasic.getUsername() + " 该时间已被预约");
        }
        reservationRecord = DozerUtils.map(updateValidate, ReservationRecord.class);
        reservationRecordMapper.updateById(reservationRecord);

    }

    @Override
    public void cancel(Long id) {
        ReservationRecord reservationRecord = reservationRepo.queryById(id);
        if(reservationRecord == null) {
            throw new OperateException("预约记录不存在");
        }
        reservationRecord.setStatus(ReservationStatusEnum.CANCEL.getCode());
        reservationRecord.setCancelTime(System.currentTimeMillis() / 1000);
        if(reservationRecord.getGroupId() == null) {
            reservationRecordMapper.updateById(reservationRecord);
        } else {
            List<ReservationRecord> reservationRecords = reservationRepo.queryByGroupIdAndDateTime(
                    reservationRecord.getGroupId(), reservationRecord.getReservationDate()
                    , reservationRecord.getReservationTime());
            reservationRepo.batchCancel(reservationRecords, System.currentTimeMillis() / 1000);
        }
    }

    @Override
    public List<String> getEnableTimeList(Long userId, String reservationDate) {
        List<ReservationRecord> reservationRecords = reservationRepo.queryByUserIdAndDateTime(userId, reservationDate, null);
        List<String> allTimeList = Lists.newArrayList();
        for(int i = 9; i < 22; i++) {
//            ReservationTimeDTO dto = new ReservationTimeDTO();
//            dto.setId(i);
            StringBuffer time = new StringBuffer();
            if(i < 10) {
                time.append(0);
            }
            time.append(i);
            time.append(":00");
//            dto.setTime(time.toString());
            allTimeList.add(time.toString());
        }
        Set<String> bookedTimes = Sets.newHashSet();
        if(CollectionUtils.isNotEmpty(reservationRecords)) {
            bookedTimes = reservationRecords.stream().map(ReservationRecord::getReservationTime).collect(Collectors.toSet());
        }
        allTimeList.removeAll(Arrays.asList(bookedTimes.toArray()));
        return allTimeList;
    }
}
