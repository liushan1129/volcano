package com.mdd.admin.controller;

import com.mdd.admin.config.aop.Log;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.admin.service.ICourseTypeService;
import com.mdd.admin.service.IGroupService;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.course.CourseTypeDetailVo;
import com.mdd.admin.vo.course.CourseTypeVo;
import com.mdd.admin.vo.group.GroupDetailVo;
import com.mdd.admin.vo.group.GroupVo;
import com.mdd.admin.vo.member.MemberListVo;
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
 * 小组表 前端控制器
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Resource
    private IGroupService iGroupService;
    /**
     * 管理员新增
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "小组新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody GroupCreateValidate createValidate) {
        iGroupService.add(createValidate);
        return AjaxResult.success();
    }
    /**
     * 课程类型所有
     *
     * @author fzr
     * @return AjaxResult<List<CourseTypeVo>>
     */
//    @GetMapping("/all")
//    public AjaxResult<List<GroupVo>> all() {
//        List<GroupDTO> list = iGroupService.all();
//        return AjaxResult.success(DozerUtils.mapList(list, GroupVo.class));
//    }

    /**
     * 小组列表
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<CourseTypeVo>>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<GroupVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated GroupSearchValidate searchValidate) {
        PageResult<GroupDTO> pageResult = iGroupService.list(pageValidate, searchValidate);
        List<GroupVo> list = DozerUtils.mapList(pageResult.getLists(), GroupVo.class);
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
    public AjaxResult<GroupDetailVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        GroupDetailVo vo = DozerUtils.map(iGroupService.detail(id), GroupDetailVo.class);
        return AjaxResult.success(vo);
    }


    /**
     * 小组编辑
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "小组编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody GroupUpdateValidate updateValidate) {
        iGroupService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @return AjaxResult<Object>
     */
    @Log(title = "小组删除")
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iGroupService.del(idValidate.getId().longValue());
        return AjaxResult.success();
    }

}
