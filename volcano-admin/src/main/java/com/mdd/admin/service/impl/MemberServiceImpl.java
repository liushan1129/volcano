package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.dto.member.MemberDetailDTO;
import com.mdd.admin.repo.CourseTypeRepo;
import com.mdd.admin.repo.MemberRepo;
import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.service.IMemberService;
import com.mdd.admin.validate.MemberCreateValidate;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.validate.MemberUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.entity.member.Member;
import com.mdd.common.enums.CourseCategoryEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.course.CourseTypeMapper;
import com.mdd.common.mapper.member.MemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.EnumUtils;
import com.mdd.common.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

    @Resource
    private MemberRepo memberRepo;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private CourseTypeRepo courseTypeRepo;
    @Resource
    private ICourseTypeService iCourseTypeService;

    @Override
    public void add(MemberCreateValidate createValidate) {
        Assert.isNull(memberRepo.queryByNameAndPhone(createValidate.getName(), createValidate.getTelephone()), "会员信息已存在");
        Member member = DozerUtils.map(createValidate, Member.class);

        member.setCreateTime(System.currentTimeMillis() / 1000);
        member.setUpdateTime(System.currentTimeMillis() / 1000);
        memberMapper.insert(member);
    }

    @Override
    public PageResult<MemberDTO> list(PageValidate pageValidate, MemberSearchValidate searchValidate) {
        IPage<MemberDTO> iPage = memberRepo.queryList(pageValidate, searchValidate);
        List<CourseTypeDTO> courseTypes = iCourseTypeService.all();
        Map<Long, CourseTypeDTO> courseTypeMap = courseTypes.stream().collect(Collectors.toMap(CourseTypeDTO::getId, courseTypeDTO -> courseTypeDTO));
        iPage.getRecords().stream().forEach(dto -> {
            dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
            dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
            dto.setGenderStr(dto.getGender() == 0 ? "女" : "男");
            CourseTypeDTO courseTypeDTO = courseTypeMap.get(dto.getCourseTypeId());
//            dto.setExpireTime(TimeUtils.timestampToDate(dto.getExpireTime()));
            dto.setCourseCategoryName(EnumUtils.getByCode(courseTypeDTO.getCategory().intValue(), CourseCategoryEnum.class));
            dto.setCourseType(courseTypeDTO);
//            dto.setIsExpired(TimeUtils.dateToTimestamp(dto.getExpireTime()) > System.currentTimeMillis() ? true : false);
            //TODO 根据groupId和memberId查询预约记录，看一共上了几节课，剩余几节课
            dto.setIsEndStr(dto.getIsEnd() == 0 ? "否[n/n]" : "是");
        });
        return PageResult.iPageHandle(iPage);
    }

    @Override
    public MemberDetailDTO detail(Long id) {
        Member member = memberRepo.queryById(id);
        MemberDetailDTO dto = DozerUtils.map(member, MemberDetailDTO.class);
        dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
        dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
//        dto.setCourseCategoryName(EnumUtils.getByCode(dto.getCourseCategory().intValue(), CourseCategoryEnum.class));
//        dto.setIsExpired(TimeUtils.dateToTimestamp(dto.getExpireTime()) > System.currentTimeMillis() ? true : false);
        return dto;
    }

    @Override
    public void edit(MemberUpdateValidate updateValidate) {
        Member member = memberRepo.queryById(updateValidate.getId());
        Assert.notNull(member,"会员信息不存在");
        Member existMember = memberRepo.queryByNameAndPhone(updateValidate.getName(), updateValidate.getTelephone());
        if(existMember != null && existMember.getId() != updateValidate.getId()) {
            throw new OperateException("会员信息已存在!");
        }
        member = DozerUtils.map(updateValidate, Member.class);
        memberMapper.updateById(member);
    }

    @Override
    public void del(long id) {
        Member member = memberRepo.queryById(id);
        Assert.notNull(member,"会员信息不存在");
        memberMapper.deleteById(id);
    }

    @Override
    public List<MemberDTO> listByCondition(MemberQueryCondition queryCondition) {
        List<Member> list = memberRepo.queryByCondition(queryCondition);
        return DozerUtils.mapList(list, MemberDTO.class);
    }
}
