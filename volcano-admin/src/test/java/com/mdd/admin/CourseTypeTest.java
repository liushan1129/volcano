package com.mdd.admin;

import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.validate.CourseTypeCreateValidate;
import com.mdd.common.enums.CourseCategoryEnum;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author liushan
 * @data 2023/4/17 12:18
 */
public class CourseTypeTest extends BaseTest {

    @Resource
    ICourseTypeService courseTypeService;

    @Test
    public void addTest() {
        CourseTypeCreateValidate validate = new CourseTypeCreateValidate();
        validate.setCategory(CourseCategoryEnum.SWIMMING.getCode());
        validate.setName("一对一");
        validate.setTimes(10);
        courseTypeService.add(validate);
    }
}
