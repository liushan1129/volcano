package com.mdd.admin.controller.system;

import com.alibaba.fastjson2.JSONArray;
import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.config.aop.Log;
import com.mdd.admin.service.system.menu.IMenuService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.system.SystemMenuCreateValidate;
import com.mdd.admin.validate.system.SystemMenuUpdateValidate;
import com.mdd.admin.vo.system.menu.SystemMenuVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.validator.annotation.IDMust;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统菜单管理
 */
@RestController
@RequestMapping("api/system/menu")
public class SystemAuthMenuController {

    @Resource
    IMenuService iMenuService;

    /**
     * 获取菜单路由
     *
     *
     * @return AjaxResult<JSONArray>
     */
    @GetMapping("/route")
    public AjaxResult<JSONArray> route() {
        List<Long> roleIds = LikeAdminThreadLocal.getRoleId();
        JSONArray lists = iMenuService.selectMenuByRoleId(roleIds);
        return AjaxResult.success(lists);
    }

    /**
     * 获取菜单路由
     *
     * @return AjaxResult<JSONArray>
     */
    @GetMapping("/getOptionalMenus")
    public AjaxResult<JSONArray> getOptionalMenus() {
        List<Long> roleIds = LikeAdminThreadLocal.getRoleId();
        JSONArray lists = iMenuService.selectOptionalMenuByRoleId(roleIds);
        return AjaxResult.success(lists);
    }

    /**
     * 获取菜单列表
     *
     * @return AjaxResult<JSONArray>
     */
    @GetMapping("/list")
    public AjaxResult<JSONArray> list() {
        JSONArray lists = iMenuService.list();
        return AjaxResult.success(lists);
    }

    /**
     * 获取菜单详情
     *
     * @param id 主键
     * @return AjaxResult<SystemAuthMenuVo>
     */
    @GetMapping("/detail")
    public AjaxResult<SystemMenuVo> detail(@Validated @IDMust() @RequestParam("id") Integer id) {
        SystemMenuVo vo = iMenuService.detail(id);
        return AjaxResult.success(vo);
    }

    /**
     * 新增菜单
     *
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "菜单新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody SystemMenuCreateValidate createValidate) {
        iMenuService.add(createValidate);
        return AjaxResult.success();
    }

    /**
     * 更新菜单
     *
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "菜单编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody SystemMenuUpdateValidate updateValidate) {
        iMenuService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 删除菜单
     *
     * @param idValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "菜单删除")
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        iMenuService.del(idValidate.getId().longValue());
        return AjaxResult.success();
    }

}
