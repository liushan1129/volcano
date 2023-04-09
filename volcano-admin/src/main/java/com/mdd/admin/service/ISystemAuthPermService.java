package com.mdd.admin.service;

import java.util.List;

/**
 * 系统角色菜单服务接口类
 */
public interface ISystemAuthPermService {

    /**
     * 根据角色ID获取菜单ID
     *
     * @param roleIds 角色ID
     * @return List<Integer>
     */
    List<Long> selectMenuIdsByRoleId(List<Long> roleIds);

    /**
     * 批量写入角色菜单
     *
     * @author fzr
     * @param roleId 角色ID
     * @param menuIds 菜单ID组
     */
    void batchSaveByMenuIds(Long roleId, String menuIds);

    /**
     * 根据角色ID批量删除角色菜单
     *
     * @author fzr
     * @param roleId 角色ID
     */
    void batchDeleteByRoleId(Long roleId);

    /**
     * 根据菜单ID批量删除角色菜单
     *
     * @author fzr
     * @param menuId 菜单ID
     */
    void batchDeleteByMenuId(Long menuId);

    /**
     * 缓存角色菜单
     *
     * @author fzr
     * @param roleId 角色ID
     */
    void cacheRoleMenusByRoleId(Long roleId);

}
