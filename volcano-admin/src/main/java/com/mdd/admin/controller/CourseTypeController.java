package com.mdd.admin.controller;

import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.config.aop.Log;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.system.role.SystemRoleDTO;
import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.validate.CourseTypeCreateValidate;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.CourseTypeUpdateValidate;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemUserUpdateValidate;
import com.mdd.admin.vo.course.CourseTypeDetailVo;
import com.mdd.admin.vo.course.CourseTypeVo;
import com.mdd.admin.vo.system.role.SystemRoleVo;
import com.mdd.admin.vo.system.user.SystemUserDetailVo;
import com.mdd.admin.vo.system.user.SystemUserListVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.validator.annotation.IDMust;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程类型表 前端控制器
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/api/courseType")
public class CourseTypeController {

    @Resource
    private ICourseTypeService iCourseTypeService;
    /**
     * 管理员新增
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "课程类型新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody CourseTypeCreateValidate createValidate) {
        iCourseTypeService.add(createValidate);
        return AjaxResult.success();
    }
    /**
     * 课程类型所有
     *
     * @author fzr
     * @return AjaxResult<List<CourseTypeVo>>
     */
    @GetMapping("/all")
    public AjaxResult<List<CourseTypeVo>> all() {
        List<CourseTypeDTO> list = iCourseTypeService.all();
        return AjaxResult.success(DozerUtils.mapList(list, CourseTypeVo.class));
    }

    /**
     * 课程类型列表
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<CourseTypeVo>>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<CourseTypeVo>> list(@Validated PageValidate pageValidate,
                                                         @Validated CourseTypeSearchValidate searchValidate) {
        PageResult<CourseTypeDTO> pageResult = iCourseTypeService.list(pageValidate, searchValidate);
        List<CourseTypeVo> list = DozerUtils.mapList(pageResult.getLists(), CourseTypeVo.class);
        PageResult result = new PageResult<>();
        result.setCount(pageResult.getCount());
        result.setPageNo(pageResult.getPageNo());
        result.setPageSize(pageResult.getPageSize());
        result.setLists(list);
        return AjaxResult.success(result);
    }

    /**
     * 课程详情
     *
     * @author fzr
     * @param id 主键
     * @return AjaxResult<SystemAuthAdminDetailVo>
     */
    @GetMapping("/detail")
    public AjaxResult<CourseTypeDetailVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        CourseTypeDetailVo vo = DozerUtils.map(iCourseTypeService.detail(id), CourseTypeDetailVo.class);
        return AjaxResult.success(vo);
    }


    /**
     * 课程类型编辑
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "课程类型编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody CourseTypeUpdateValidate updateValidate) {
        iCourseTypeService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @return AjaxResult<Object>
     */
    @Log(title = "课程类型删除")
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iCourseTypeService.del(idValidate.getId().longValue());
        return AjaxResult.success();
    }
}
