package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.entity.system.SystemAuthRole;
import com.mdd.common.mapper.course.CourseTypeMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/17 12:11
 */
@Component
public class CourseTypeRepo {

    @Resource
    private CourseTypeMapper courseTypeMapper;

    public CourseType queryByCategoryAndName(Integer category, String name) {
        QueryWrapper wrapper = new QueryWrapper<CourseType>()
                .eq("category", category)
                .eq("name", name)
                .eq("is_delete", 0);
        return  courseTypeMapper.selectOne(wrapper);
    }

    public List<CourseType> queryAllCourseType(String[] fields) {
        QueryWrapper<CourseType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        if(org.assertj.core.util.Arrays.isNullOrEmpty(fields)) {
            queryWrapper.select();
        }else {
            queryWrapper.select(fields);
        }
//        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));
        return courseTypeMapper.selectList(queryWrapper);

    }
    public IPage<CourseTypeDTO> queryList(PageValidate pageValidate, CourseTypeSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<CourseType> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.name, t.category, t.times, t.create_time, t.update_time")
                .eq("t.is_delete", 0)
                .orderByDesc(Arrays.asList("t.id", "t.category"));

        courseTypeMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:category:int"
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
