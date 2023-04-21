package com.mdd.admin.service;

import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.validate.CourseTypeCreateValidate;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.CourseTypeUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.course.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程类型表 服务类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
public interface ICourseTypeService extends IService<CourseType> {

    /**
     * 添加课程
     * @param createValidate
     */
    void add(CourseTypeCreateValidate createValidate);

    PageResult<CourseTypeDTO> list(PageValidate pageValidate, CourseTypeSearchValidate searchValidate);

    CourseTypeDTO detail(Long id);

    void edit(CourseTypeUpdateValidate updateValidate);

    void del(long longValue);

    List<CourseTypeDTO> all();
}
