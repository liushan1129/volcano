package com.mdd.admin.controller.system;

import com.mdd.admin.service.system.log.ILogsServer;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemSearchLoginsValidate;
import com.mdd.admin.validate.system.SystemSearchOperateValidate;
import com.mdd.admin.vo.system.log.SystemLogLoginVo;
import com.mdd.admin.vo.system.log.SystemLogOperateVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统日志管理
 */
@RestController
@RequestMapping("api/system/log")
public class SystemLogsController {

    @Resource
    ILogsServer iSystemLogsServer;

    /**
     * 系统操作日志
     *
     * @author fzr
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<LogOperateVo>>
     */
    @GetMapping("/operate")
    public AjaxResult<PageResult<SystemLogOperateVo>> operate(@Validated PageValidate pageValidate,
                                                              @Validated SystemSearchOperateValidate searchValidate) {
        PageResult<SystemLogOperateVo> list = iSystemLogsServer.operate(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

    /**
     * 系统登录日志
     *
     * @author fzr
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<LogLoginVo>>
     */
    @GetMapping("/login")
    public AjaxResult<PageResult<SystemLogLoginVo>> login(@Validated PageValidate pageValidate,
                                                          @Validated SystemSearchLoginsValidate searchValidate) {
        PageResult<SystemLogLoginVo> list = iSystemLogsServer.login(pageValidate, searchValidate);
        return AjaxResult.success(list);
    }

}
