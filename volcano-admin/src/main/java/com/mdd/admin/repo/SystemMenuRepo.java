package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.system.SystemAuthMenu;
import com.mdd.common.mapper.system.SystemAuthMenuMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 22:34
 */
@Component
public class SystemMenuRepo {
    @Resource
    private SystemAuthMenuMapper systemAuthMenuMapper;
    public List<SystemAuthMenu> queryByIdAndType(List<Long> menuIds, List<String> types) {
        List<SystemAuthMenu> systemAuthMenus = systemAuthMenuMapper.selectList(new QueryWrapper<SystemAuthMenu>()
                .eq("is_disable", 0)
                .in("id", menuIds)
                .in("menu_type", types)
                .orderByAsc("menu_sort").orderByAsc( "id"));
        return systemAuthMenus;
    }

    public List<SystemAuthMenu> queryByRoleIdAndMenuType(List<Long> menuIds, List<Long> roleIds, ArrayList<String> menuType) {

        QueryWrapper<SystemAuthMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("menu_type", menuType);
        queryWrapper.eq("is_disable", 0);
        queryWrapper.orderByDesc("menu_sort");
        queryWrapper.orderByAsc("id");
        if (!CollectionUtils.isEmpty(roleIds)) {
            queryWrapper.in("id", menuIds);
        }

        return systemAuthMenuMapper.selectList(queryWrapper);

    }

    public List<SystemAuthMenu> queryAll(Long isDisable) {
        QueryWrapper<SystemAuthMenu> queryWrapper = new QueryWrapper<>();
        if(isDisable != null) {
            queryWrapper.eq("is_disable", 0);
        }
        queryWrapper.orderByDesc("menu_sort");
        queryWrapper.orderByAsc("id");
        return systemAuthMenuMapper.selectList(queryWrapper);

    }

    public SystemAuthMenu queryById(Integer id) {
        return systemAuthMenuMapper.selectOne(new QueryWrapper<SystemAuthMenu>().eq("id", id));

    }

    public SystemAuthMenu queryFieldById(Long id, String[] fields) {
        return systemAuthMenuMapper.selectOne(
                new QueryWrapper<SystemAuthMenu>()
                        .select(fields)
                        .eq("id", id)
                        .last("limit 1"));
    }

    public Object queryByPid(Long id) {
       return systemAuthMenuMapper.selectOne(
                new QueryWrapper<SystemAuthMenu>()
                        .eq("pid", id)
                        .last("limit 1"));
    }
}
