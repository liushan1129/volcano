package com.mdd.admin.service.system.menu;

import com.alibaba.fastjson2.JSONArray;
import com.mdd.admin.validate.system.SystemMenuCreateValidate;
import com.mdd.admin.validate.system.SystemMenuUpdateValidate;
import com.mdd.admin.vo.system.menu.SystemMenuVo;

import java.util.List;

/**
 * 系统菜单服务接口类
 */
public interface IMenuService {

    /**
     * 根据角色获取菜单
     *
     * @author fzr
     * @return JSONArray
     */
    JSONArray selectMenuByRoleId(List<Long> roleId);

    /**
     * 菜单列表
     *
     * @author fzr
     * @return JSONArray
     */
    JSONArray list();

    /**
     * 获取可见菜单
     * @param roleIds
     * @return
     */
    JSONArray selectOptionalMenuByRoleId(List<Long> roleIds);


    /**
     * 菜单详情
     *
     * @author fzr
     * @param id 主键
     * @return SysMenu
     */
    SystemMenuVo detail(Integer id);

    /**
     * 菜单新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    void add(SystemMenuCreateValidate createValidate);

    /**
     * 菜单编辑
     *
     * @author fzr
     * @param updateValidate 参数
     */
    void edit(SystemMenuUpdateValidate updateValidate);

    /**
     * 菜单删除
     *
     * @author fzr
     * @param id 主键
     */
    void del(Long id);
}
