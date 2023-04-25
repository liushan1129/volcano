package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.admin.dto.group.GroupDetailDTO;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.dto.member.MemberDetailDTO;
import com.mdd.admin.repo.CourseTypeRepo;
import com.mdd.admin.repo.GroupRepo;
import com.mdd.admin.repo.MemberRepo;
import com.mdd.admin.repo.SystemUserRepo;
import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.service.IGroupService;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.GroupQueryCondition;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.member.Member;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.enums.CourseCategoryEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.group.GroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdd.common.entity.group.Group;
import com.mdd.common.mapper.member.MemberMapper;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.EnumUtils;
import com.mdd.common.util.StringUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * 小组表 服务实现类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {
    @Resource
    private GroupRepo groupRepo;

    @Resource
    private GroupMapper groupMapper;

    @Resource
    private SystemUserRepo userRepo;

    @Resource
    private MemberRepo memberRepo;

    @Override
    public void add(GroupCreateValidate createValidate) {
        Assert.isNull(groupRepo.queryByName(createValidate.getName()), "小组名称已存在");
        UserBasic userBasic = userRepo.queryById(createValidate.getUserId());
        List<Member> memberList = memberRepo.queryByIds(createValidate.getMemberIds());
        Group group = DozerUtils.map(createValidate, Group.class);
        String memberIds = createValidate.getMemberIds().toString();
        group.setMemberIds(memberIds.substring(1, memberIds.length() -1));
        group.setUsername(userBasic.getUsername());
        String memberNames = memberList.stream().map(Member::getName).collect(Collectors.toList()).toString();
        group.setMemberNames(memberNames.substring(1, memberNames.length() - 1));
        group.setCreateTime(System.currentTimeMillis() / 1000);
        group.setUpdateTime(System.currentTimeMillis() / 1000);
        groupMapper.insert(group);
    }

    @Override
    public PageResult<GroupDTO> list(PageValidate pageValidate, GroupSearchValidate searchValidate) {
        IPage<GroupDTO> iPage = groupRepo.queryList(pageValidate, searchValidate);
        iPage.getRecords().stream().forEach(dto -> {
            dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
            dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
        });
        return PageResult.iPageHandle(iPage);
    }

    @Override
    public GroupDetailDTO detail(Long id) {
        Group group = groupRepo.queryById(id);
        GroupDetailDTO dto = DozerUtils.map(group, GroupDetailDTO.class);
        dto.setMemberIdList(Arrays.stream(group.getMemberIds().split(",")).map(idStr -> Long.parseLong(idStr.trim())).collect(Collectors.toList()));
        dto.setMemberNameList(Arrays.asList(group.getMemberNames().split(",")));
        dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
        dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
//        dto.setCourseCategoryName(EnumUtils.getByCode(dto.getCourseCategory().intValue(), CourseCategoryEnum.class));
//        dto.setIsExpired(TimeUtils.dateToTimestamp(dto.getExpireTime()) > System.currentTimeMillis() ? true : false);
        return dto;
    }

    @Override
    public void edit(GroupUpdateValidate updateValidate) {
        Group group = groupRepo.queryById(updateValidate.getId());
        Assert.notNull(group,"小组信息不存在");
        Group existGroup = groupRepo.queryByName(updateValidate.getName());
        if(existGroup != null && existGroup.getId() != updateValidate.getId()) {
            throw new OperateException("小组信息已存在!");
        }
        group = DozerUtils.map(updateValidate, Group.class);
        groupMapper.updateById(group);
    }

    @Override
    public void del(long id) {
        Group group = groupRepo.queryById(id);
        Assert.notNull(group,"小组信息不存在");
        groupMapper.deleteById(id);
    }

    @Override
    public List<GroupDTO> listByCondition(GroupQueryCondition queryCondition) {
        List<Group> list = groupRepo.queryByCondition(queryCondition);
        return DozerUtils.mapList(list, GroupDTO.class);
    }
}
