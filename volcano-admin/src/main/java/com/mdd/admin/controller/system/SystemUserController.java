package com.mdd.admin.controller.system;

import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.config.aop.Log;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.service.system.user.IUserService;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemUserCreateValidate;
import com.mdd.admin.validate.system.SystemUserSearchValidate;
import com.mdd.admin.validate.system.SystemUserUpInfoValidate;
import com.mdd.admin.validate.system.SystemUserUpdateValidate;
import com.mdd.admin.validate.system.condition.SystemUserQueryCondition;
import com.mdd.admin.vo.system.user.SystemUserDetailVo;
import com.mdd.admin.vo.system.user.SystemUserListVo;
import com.mdd.admin.vo.system.user.SystemUserSelvesVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.validator.annotation.IDMust;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统管理员管理
 */
@RestController
@RequestMapping("api/system/admin")
public class SystemUserController {

    @Resource
    IUserService iUserService;

    /**
     * 管理员列表
     ** @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<SystemAuthAdminListedVo>>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<SystemUserListVo>> list(@Validated PageValidate pageValidate,
                                                         @Validated SystemUserSearchValidate searchValidate) {
        PageResult<SystemUserBasicListDTO> pageResult = iUserService.list(pageValidate, searchValidate);
        List<SystemUserListVo> list = DozerUtils.mapList(pageResult.getLists(), SystemUserListVo.class);
        PageResult result = new PageResult<>();
        result.setCount(pageResult.getCount());
        result.setPageNo(pageResult.getPageNo());
        result.setPageSize(pageResult.getPageSize());
        result.setLists(list);
        return AjaxResult.success(result);
    }

    @GetMapping("/getListByCondition")
    public AjaxResult<List<SystemUserListVo>> getListByCondition(@Validated SystemUserQueryCondition queryCondition) {
        List<SystemUserBasicListDTO> list = iUserService.listByCondition(queryCondition);
        List<SystemUserListVo> result = DozerUtils.mapList(list, SystemUserListVo.class);
        return AjaxResult.success(result);
    }

    /**
     * 管理员信息
     ** @return AjaxResult<SystemUserSelvesVo>
     */
    @GetMapping("/self")
    public AjaxResult<SystemUserSelvesVo> self() {
        Long adminId = LikeAdminThreadLocal.getAdminId();
        long before = System.currentTimeMillis();
        SystemUserSelvesVo vo = DozerUtils.map(iUserService.self(adminId), SystemUserSelvesVo.class);
        long behind = System.currentTimeMillis();
        System.out.println("..........查詢列表耗時：" + (behind - before));
        return AjaxResult.success(vo);
    }

    /**
     * 管理员详情
     *
     * @param id 主键
     * @return AjaxResult<SystemAuthAdminDetailVo>
     */
    @GetMapping("/detail")
    public AjaxResult<SystemUserDetailVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        SystemUserDetailVo vo = DozerUtils.map(iUserService.detail(id), SystemUserDetailVo.class);
        vo.setGender(vo.getGender().toString());
        return AjaxResult.success(vo);
    }

    /**
     * 管理员新增
     *
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "管理员新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody SystemUserCreateValidate createValidate) {
        iUserService.add(createValidate);
        return AjaxResult.success();
    }

    /**
     * 管理员编辑
     *
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "管理员编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody SystemUserUpdateValidate updateValidate) {
        //获取当前登录用户角色
        List<Long> roleIds = LikeAdminThreadLocal.getRoleId();
        iUserService.edit(updateValidate, roleIds);
        return AjaxResult.success();
    }

    /**
     * 当前管理员更新
     * @return AjaxResult<Object>
     */
    @Log(title = "管理员更新")
    @PostMapping("/upInfo")
    public AjaxResult<Object> upInfo(@Validated @RequestBody SystemUserUpInfoValidate upInfoValidate) {
        Long adminId = LikeAdminThreadLocal.getAdminId();
        iUserService.upInfo(upInfoValidate, adminId);
        return AjaxResult.success();
    }

    /**
     * 管理员删除
     * @return AjaxResult<Object>
     */
    @Log(title = "管理员删除")
    @PostMapping("/del")
    public AjaxResult<Object> del(@Validated @RequestBody IdValidate idValidate) {
        Long loginId = LikeAdminThreadLocal.getAdminId();
        List<Long> roleIds = LikeAdminThreadLocal.getRoleId();
        iUserService.del(idValidate.getId().longValue(), roleIds, loginId);
        return AjaxResult.success();
    }

    /**
     * 管理员状态切换
     * @return AjaxResult<Object>
     */
    @Log(title = "管理员状态")
    @PostMapping("/disable")
    public AjaxResult<Object> disable(@Validated @RequestBody IdValidate idValidate) {
        Long adminId = LikeAdminThreadLocal.getAdminId();
        iUserService.disable(idValidate.getId().longValue(), adminId);
        return AjaxResult.success();
    }
}
