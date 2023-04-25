package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.entity.member.Member;
import com.mdd.common.mapper.member.MemberMapper;
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
public class MemberRepo {

    @Resource
    private MemberMapper memberMapper;

    public Member queryByNameAndPhone(String name, String telephone) {
        QueryWrapper wrapper = new QueryWrapper<Member>()
                .eq("telephone", telephone)
                .eq("name", name)
                .eq("is_delete", 0);
        return  memberMapper.selectOne(wrapper);
    }

    public IPage<MemberDTO> queryList(PageValidate pageValidate, MemberSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<Member> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.name, t.gender, t.age, t.weight, t.height, t.telephone, t.course_type_id, t.is_end, t.expire_time, t.create_time, t.update_time")
                .eq("t.is_delete", 0)
                .orderByDesc(Arrays.asList("t.id", "t.name"));

        memberMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:gender:int",
                "=:telephone:str",
                "=:isEnd@is_end:int",
                "=:courseTypeId@course_type_id:long"
        });

        IPage<MemberDTO> iPage = memberMapper.selectJoinPage(
                new Page<>(page, limit),
                MemberDTO.class,
                mpjQueryWrapper);

        return iPage;
    }
    public Member queryById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper<CourseType>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_delete", 0);
        return memberMapper.selectOne(queryWrapper);
    }

    public List<Member> all() {
        QueryWrapper queryWrapper = new QueryWrapper<Member>().eq("is_delete", 0);
        return memberMapper.selectList(queryWrapper);
    }

    public List<Member> queryByIds(List<Long> memberIds) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>()
                .in("id", memberIds)
                .eq("is_delete", 0);
        return memberMapper.selectList(queryWrapper);
    }

    public List<Member> queryByCondition(MemberQueryCondition queryCondition) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>()
                .eq("is_delete", 0);
        if(queryCondition.getId() != null) {
            queryWrapper.eq("id", queryCondition.getId());
        }
        if(StringUtils.isNotBlank(queryCondition.getName())) {
            queryWrapper.like("name", queryCondition.getName());
        }
        return memberMapper.selectList(queryWrapper);
    }

    public Member queryByName(String memberName) {
        QueryWrapper queryWrapper = new QueryWrapper<Member>()
                .eq("name", memberName)
                .eq("is_delete", 0);
        return memberMapper.selectOne(queryWrapper);
    }
}
