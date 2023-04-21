package com.mdd.admin.controller;

import com.mdd.admin.config.aop.Log;
import com.mdd.admin.dto.course.CourseTypeDTO;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.service.IMemberService;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.admin.validate.system.condition.SystemUserQueryCondition;
import com.mdd.admin.vo.course.CourseTypeDetailVo;
import com.mdd.admin.vo.course.CourseTypeVo;
import com.mdd.admin.vo.member.MemberDetailVo;
import com.mdd.admin.vo.member.MemberListVo;
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
 * 会员表 前端控制器
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Resource
    private IMemberService iMemberService;


    @GetMapping("/getListByCondition")
    public AjaxResult<List<MemberListVo>> getListByCondition(@Validated MemberQueryCondition queryCondition) {
        List<MemberDTO> list = iMemberService.listByCondition(queryCondition);
        List<MemberListVo> result = DozerUtils.mapList(list, MemberListVo.class);
        return AjaxResult.success(result);
    }


    /**
     * 管理员新增
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "会员新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody MemberCreateValidate createValidate) {
        iMemberService.add(createValidate);
        return AjaxResult.success();
    }

    /**
     * 会员列表
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<CourseTypeVo>>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<MemberListVo>> list(@Validated PageValidate pageValidate,
                                                     @Validated MemberSearchValidate searchValidate) {
        PageResult<MemberDTO> pageResult = iMemberService.list(pageValidate, searchValidate);
        List<MemberListVo> list = DozerUtils.mapList(pageResult.getLists(), MemberListVo.class);
        PageResult result = new PageResult<>();
        result.setCount(pageResult.getCount());
        result.setPageNo(pageResult.getPageNo());
        result.setPageSize(pageResult.getPageSize());
        result.setLists(list);
        return AjaxResult.success(result);
    }

    /**
     * 会员详情
     *
     * @author fzr
     * @param id 主键
     * @return AjaxResult<SystemAuthAdminDetailVo>
     */
    @GetMapping("/detail")
    public AjaxResult<MemberDetailVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        MemberDetailVo vo = DozerUtils.map(iMemberService.detail(id), MemberDetailVo.class);
        return AjaxResult.success(vo);
    }


    /**
     * 会员编辑
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "会员编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody MemberUpdateValidate updateValidate) {
        iMemberService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 删除
     * @return AjaxResult<Object>
     */
    @Log(title = "会员删除")
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iMemberService.del(idValidate.getId().longValue());
        return AjaxResult.success();
    }

}
