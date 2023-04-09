package com.mdd.admin.service.system.menu.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.google.common.collect.Lists;
import com.mdd.admin.LikeAdminThreadLocal;
import com.mdd.admin.config.AdminConfig;
import com.mdd.admin.dto.system.menu.SystemMenuDTO;
import com.mdd.admin.repo.SystemMenuRepo;
import com.mdd.admin.service.system.menu.IMenuService;
import com.mdd.admin.service.ISystemAuthPermService;
import com.mdd.admin.validate.system.SystemMenuCreateValidate;
import com.mdd.admin.validate.system.SystemMenuUpdateValidate;
import com.mdd.admin.vo.system.menu.SystemMenuVo;
import com.mdd.common.entity.system.SystemAuthMenu;
import com.mdd.common.mapper.system.SystemAuthMenuMapper;
import com.mdd.common.util.ArrayUtils;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.RedisUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统菜单服务实现类
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    SystemAuthMenuMapper systemAuthMenuMapper;
    @Resource
    SystemMenuRepo systemMenuRepo;

    @Resource
    ISystemAuthPermService iSystemAuthPermService;

    /**
     * 根据角色ID获取菜单
     *
     * @author fzr
     * @param roleIds 角色ID
     * @return JSONArray
     */
    @Override
    public JSONArray selectMenuByRoleId(List<Long> roleIds) {
        List<Long> menuIds = iSystemAuthPermService.selectMenuIdsByRoleId(roleIds);
        if (menuIds.size() <= 0) {
            menuIds.add(0L);
        }
        List<SystemAuthMenu> systemAuthMenus = systemMenuRepo.queryByRoleIdAndMenuType(menuIds, roleIds, Lists.newArrayList("M", "C"));
        List<SystemMenuDTO> list = DozerUtils.mapList(systemAuthMenus, SystemMenuDTO.class);
        setOperateTime(list);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(list));
        return ArrayUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    private void setOperateTime(List<SystemMenuDTO> list) {
        list.stream().forEach(dto -> {
            dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
            dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
        });
    }

    /**
     * 菜单列表
     *
     * @author fzr
     * @return JSONArray
     */
    @Override
    public JSONArray list() {
        List<SystemAuthMenu> systemAuthMenus = systemMenuRepo.queryAll(null);
        List<SystemMenuDTO> list = DozerUtils.mapList(systemAuthMenus, SystemMenuDTO.class);
        setOperateTime(list);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(list));
        return ArrayUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    @Override
    public JSONArray selectOptionalMenuByRoleId(List<Long> roleIds) {
        List<SystemAuthMenu> systemAuthMenus =  systemMenuRepo.queryAll(0L);

        List<SystemMenuDTO> list = DozerUtils.mapList(systemAuthMenus, SystemMenuDTO.class);
        setOperateTime(list);
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(list));
        return ArrayUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    /**
     * 菜单详情
     *
     * @author fzr
     * @param id 主键参数
     * @return SysMenu
     */
    @Override
    public SystemMenuVo detail(Integer id) {
        SystemAuthMenu systemAuthMenu = systemAuthMenuMapper.selectOne(new QueryWrapper<SystemAuthMenu>().eq("id", id));
        Assert.notNull(systemAuthMenu, "菜单已不存在!");

        SystemMenuVo vo  = new SystemMenuVo();
        BeanUtils.copyProperties(systemAuthMenu, vo);
        vo.setCreateTime(TimeUtils.timestampToDate(systemAuthMenu.getCreateTime()));
        vo.setUpdateTime(TimeUtils.timestampToDate(systemAuthMenu.getUpdateTime()));

        return vo;
    }

    /**
     * 新增菜单
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    public void add(SystemMenuCreateValidate createValidate) {
        SystemAuthMenu model = DozerUtils.map(createValidate, SystemAuthMenu.class);
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthMenuMapper.insert(model);

        RedisUtils.del(AdminConfig.backstageRolesKey);
    }

    /**
     * 编辑菜单
     *
     * @author fzr
     * @param updateValidate 菜单
     */
    @Override
    public void edit(SystemMenuUpdateValidate updateValidate) {
        SystemAuthMenu model = systemMenuRepo.queryById(updateValidate.getId());
        Assert.notNull(model, "菜单已不存在!");
        model = DozerUtils.map(updateValidate, SystemAuthMenu.class);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthMenuMapper.updateById(model);

        RedisUtils.del(AdminConfig.backstageRolesKey);
    }

    /**
     * 删除菜单
     *
     * @author fzr
     * @param id 主键参数
     */
    @Override
    public void del(Long id) {
        String[] fields = new String[]{"id", "pid", "menu_name"};
        SystemAuthMenu model = systemMenuRepo.queryFieldById(id, fields);
        Assert.notNull(model, "菜单已不存在!");
        Assert.isNull(systemMenuRepo.queryByPid(id), "请先删除子菜单再操作!");
        systemAuthMenuMapper.deleteById(id);
        iSystemAuthPermService.batchDeleteByMenuId(id);

        RedisUtils.del(AdminConfig.backstageRolesKey);
    }

}
