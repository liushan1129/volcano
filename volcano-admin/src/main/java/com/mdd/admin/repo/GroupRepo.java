package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.GroupSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.GroupQueryCondition;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.entity.group.Group;
import com.mdd.common.entity.member.Member;
import com.mdd.common.mapper.course.CourseTypeMapper;
import com.mdd.common.mapper.group.GroupMapper;
import com.mdd.common.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 12:11
 */
@Component
public class GroupRepo {

    @Resource
    private GroupMapper groupMapper;

    public Group queryByName(String name) {
        QueryWrapper wrapper = new QueryWrapper<Group>()
                .eq("name", name)
                .eq("is_delete", 0);
        return  groupMapper.selectOne(wrapper);
    }

    public IPage<GroupDTO> queryList(PageValidate pageValidate, GroupSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<Group> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.name, t.user_id, t.username, t.member_ids, t.member_names, t.create_time, t.update_time")
                .eq("t.is_delete", 0)
                .orderByDesc(Arrays.asList("t.id"));

        groupMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:username:str",
                "like:memberName@member_names:str"
        });

        IPage<GroupDTO> iPage = groupMapper.selectJoinPage(
                new Page<>(page, limit),
                GroupDTO.class,
                mpjQueryWrapper);

        return iPage;
    }

    public Group queryById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper<Group>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_delete", 0);
        return groupMapper.selectOne(queryWrapper);
    }

    public List<Group> queryByCondition(GroupQueryCondition queryCondition) {
        QueryWrapper queryWrapper = new QueryWrapper<Group>();
        if(queryCondition != null && queryCondition.getId() != null) {
            queryWrapper.eq("id", queryCondition.getId());
        }
        if(queryCondition != null && StringUtils.isNotBlank(queryCondition.getName())) {
            queryWrapper.like("name", queryCondition.getName());

        }
        queryWrapper.eq("is_delete", 0);
        return groupMapper.selectList(queryWrapper);
    }
}
