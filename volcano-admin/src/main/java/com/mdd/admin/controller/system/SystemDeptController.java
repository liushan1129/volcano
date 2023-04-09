package com.mdd.admin.controller.system;

import com.alibaba.fastjson2.JSONArray;
import com.mdd.admin.dto.system.dept.SystemDeptDTO;
import com.mdd.admin.service.system.dept.IDeptService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.system.SystemDeptCreateValidate;
import com.mdd.admin.validate.system.SystemDeptSearchValidate;
import com.mdd.admin.validate.system.SystemDeptUpdateValidate;
import com.mdd.admin.vo.system.dept.SystemDeptVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.validator.annotation.IDMust;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统部门管理
 */
@RestController
@RequestMapping("api/system/dept")
public class SystemDeptController {

    @Resource
    IDeptService iSystemAuthDeptService;

    /**
     * 部门所有
     *
     * @author fzr
     * @return AjaxResult<List<SystemAuthDeptVo>>
     */
    @GetMapping("/all")
    public AjaxResult<List<SystemDeptVo>> all() {
        List<SystemDeptDTO> list = iSystemAuthDeptService.all();
        List<SystemDeptVo> result = DozerUtils.mapList(list, SystemDeptVo.class);
        return AjaxResult.success(result);
    }

    /**
     * 部门列表
     *
     * @author fzr
     * @param searchValidate 搜索参数
     * @return AjaxResult<JSONArray>
     */
    @GetMapping("/list")
    public AjaxResult<JSONArray> list(@Validated SystemDeptSearchValidate searchValidate) {
        JSONArray list = iSystemAuthDeptService.list(searchValidate);
        return AjaxResult.success(list);
    }

    /**
     * 部门详情
     *
     * @author fzr
     * @param id 主键
     * @return AjaxResult<SystemAuthDeptVo>
     */
    @GetMapping("/detail")
    public AjaxResult<SystemDeptVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        SystemDeptDTO dto = iSystemAuthDeptService.detail(id);
        SystemDeptVo vo = DozerUtils.map(dto, SystemDeptVo.class);
        return AjaxResult.success(vo);
    }

    /**
     * 部门新增
     *
     * @author fzr
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody SystemDeptCreateValidate createValidate) {
        iSystemAuthDeptService.add(createValidate);
        return AjaxResult.success();
    }

    /**
     * 部门编辑
     *
     * @author fzr
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody SystemDeptUpdateValidate updateValidate) {
        iSystemAuthDeptService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 部门删除
     *
     * @author fzr
     * @param idValidate 参数
     * @return AjaxResult<Object>
     */
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iSystemAuthDeptService.del(idValidate.getId().longValue());
        return AjaxResult.success();
    }

}
