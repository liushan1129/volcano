package com.mdd.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.repo.CourseTypeRepo;
import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.validate.CourseTypeCreateValidate;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.CourseTypeUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.course.CourseType;
import com.mdd.common.enums.CourseCategoryEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.course.CourseTypeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.EnumUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程类型表 服务实现类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Resource
    private CourseTypeRepo courseTypeRepo;
    @Resource
    private CourseTypeMapper courseTypeMapper;
    @Override
    public void add(CourseTypeCreateValidate createValidate) {
        Assert.isNull(courseTypeRepo.queryByCategoryAndName(createValidate.getCategory(), createValidate.getName()), "课程类型已存在，请重新命名");
        CourseType courseType = DozerUtils.map(createValidate, CourseType.class);
        courseType.setCreateTime(System.currentTimeMillis() / 1000);
        courseType.setUpdateTime(System.currentTimeMillis() / 1000);
        courseTypeMapper.insert(courseType);
    }

    @Override
    public PageResult<CourseTypeDTO> list(PageValidate pageValidate, CourseTypeSearchValidate searchValidate) {
        IPage<CourseTypeDTO> iPage = courseTypeRepo.queryList(pageValidate, searchValidate);

        iPage.getRecords().stream().forEach(courseTypeDTO -> {
            courseTypeDTO.setCreateTime(TimeUtils.timestampToDate(courseTypeDTO.getCreateTime()));
            courseTypeDTO.setCategoryName(EnumUtils.getByCode(courseTypeDTO.getCategory().intValue(), CourseCategoryEnum.class));
        });
        return PageResult.iPageHandle(iPage);
    }

    @Override
    public CourseTypeDTO detail(Long id) {
        CourseType courseType = courseTypeRepo.queryListById(id);
        Assert.notNull(courseType, "课程类型已不存在！");
        CourseTypeDTO dto = DozerUtils.map(courseType, CourseTypeDTO.class);
        dto.setCategoryName(EnumUtils.getByCode(dto.getCategory().intValue(), CourseCategoryEnum.class));
        return dto;
    }

    @Override
    public void edit(CourseTypeUpdateValidate updateValidate) {
        CourseType courseType = courseTypeRepo.queryListById(updateValidate.getId());
        Assert.notNull(courseType, "课程类型已不存在！");
        CourseType nameExistCourseType = courseTypeRepo.queryByCategoryAndName(updateValidate.getCategory(), updateValidate.getName());
        if(nameExistCourseType != null && nameExistCourseType.getId() != updateValidate.getId()) {
            throw new OperateException("该类型课程名称已存在!");
        }
        courseType = DozerUtils.map(updateValidate, CourseType.class);
        courseType.setCreateTime(System.currentTimeMillis() / 1000);
        courseType.setUpdateTime(System.currentTimeMillis() / 1000);
        courseTypeMapper.updateById(courseType);
    }

    @Override
    public void del(long id) {
        CourseType courseType = courseTypeRepo.queryListById(id);
        Assert.notNull(courseType, "课程类型已不存在！");
        courseTypeMapper.deleteById(id);
    }

    @Override
    public List<CourseTypeDTO> all() {
        String[] fields = {"id", "name", "category", "times"};
        List<CourseType> list = courseTypeRepo.queryAllCourseType(fields);
        List<CourseTypeDTO> courseTypeDTOS = DozerUtils.mapList(list, CourseTypeDTO.class);
        courseTypeDTOS.forEach(courseTypeDTO -> {
            courseTypeDTO.setCreateTime(TimeUtils.timestampToDate(courseTypeDTO.getCreateTime()));
            courseTypeDTO.setCategoryName(EnumUtils.getByCode(courseTypeDTO.getCategory().intValue(), CourseCategoryEnum.class));
        });

        return courseTypeDTOS;
    }
}
