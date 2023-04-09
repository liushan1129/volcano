package com.mdd.admin.controller.setting;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.service.ISettingStorageService;
import com.mdd.common.core.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 存储方式配置管理
 */
@RestController
@RequestMapping("api/setting/storage")
public class SettingStorageController {

    @Resource
    ISettingStorageService iSettingStorageService;

    /**
     * 存储列表
     *
     * @author fzr
     * @return AjaxResult<List<Map<String, Object>>>
     */
    @GetMapping("/list")
    public AjaxResult<List<Map<String, Object>>> list() {
        List<Map<String, Object>> list = iSettingStorageService.list();
        return AjaxResult.success(list);
    }

    /**
     * 存储详情
     *
     * @param alias 引擎别名
     * @return AjaxResult<Map<String, Object>>
     */
    @GetMapping("/detail")
    public AjaxResult<Map<String, Object>> detail(String alias) {
        Map<String, Object> map = iSettingStorageService.detail(alias);
        return AjaxResult.success(map);
    }

    /**
     * 存储编辑
     *
     * @author fzr
     * @param params 参数
     * @return AjaxResult<Object>
     */
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@RequestBody Map<String, String> params) {
        iSettingStorageService.edit(params);
        return AjaxResult.success();
    }

    /**
     * 存储切换
     *
     * @author fzr
     * @param params 参数
     * @return AjaxResult<Object>
     */
    @PostMapping("/change")
    public AjaxResult<Object> change(@RequestBody Map<String, String> params) {
        Assert.notNull(params.get("alias"), "alias参数缺失");
        Assert.notNull(params.get("status"), "status参数缺失");
        String alias = params.get("alias");
        Integer status = Integer.parseInt(params.get("status"));
        iSettingStorageService.change(alias, status);
        return AjaxResult.success();
    }

}
