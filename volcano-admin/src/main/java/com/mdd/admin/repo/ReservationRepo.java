package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.mapper.course.CourseTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author liushan
 * @data 2023/4/17 12:11
 */
@Component
public class ReservationRepo {

    @Resource
    private CourseTypeMapper courseTypeMapper;

    public CourseType queryByTypeAndName(Integer type, String name) {
        QueryWrapper wrapper = new QueryWrapper<CourseType>()
//                .eq("type", type)
                .eq("name", name);
        return  courseTypeMapper.selectOne(wrapper);
    }

    public IPage<CourseTypeDTO> queryList(PageValidate pageValidate, CourseTypeSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<CourseType> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.name,  t.times, t.create_time, t.update_time")
                .eq("t.is_delete", 0)
                .orderByDesc(Arrays.asList("t.id", "t.type"));

        courseTypeMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:type:int"
        });

        IPage<CourseTypeDTO> iPage = courseTypeMapper.selectJoinPage(
                new Page<>(page, limit),
                CourseTypeDTO.class,
                mpjQueryWrapper);

        return iPage;
    }

    public CourseType queryListById(Long id) {
        QueryWrapper queryWrapper = new QueryWrapper<CourseType>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_delete", 0);
        return courseTypeMapper.selectOne(queryWrapper);
    }
}
