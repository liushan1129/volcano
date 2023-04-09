package com.mdd.admin.service.system.role.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mdd.admin.config.AdminConfig;
import com.mdd.admin.dto.system.role.SystemRoleDTO;
import com.mdd.admin.service.ISystemAuthPermService;
import com.mdd.admin.service.system.role.IRoleService;
import com.mdd.admin.repo.SystemRoleRepo;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemRoleCreateValidate;
import com.mdd.admin.validate.system.SystemRoleUpdateValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.system.SystemAuthRole;
import com.mdd.common.mapper.system.SystemUserBasicMapper;
import com.mdd.common.mapper.system.SystemAuthRoleMapper;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.RedisUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统角色服务实现类
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    SystemUserBasicMapper userBasicMapper;


    @Resource
    SystemAuthRoleMapper systemAuthRoleMapper;

    @Resource
    SystemRoleRepo systemRoleRepo;

    @Resource
    ISystemAuthPermService iSystemAuthPermService;

    /**
     * 角色所有
     *
     * @author fzr
     * @return List<SystemAuthRoleVo>
     */
    @Override
    public List<SystemRoleDTO> all() {
        String[] fields = new String[] {"id", "name", "sort", "is_disable", "create_time", "update_time"};
        List<SystemAuthRole> roles = systemRoleRepo.queryAllRole(fields);
        List<SystemRoleDTO> list = DozerUtils.mapList(roles, SystemRoleDTO.class);
        list.stream().forEach(systemRoleDTO -> {
            systemRoleDTO.setCreateTime(TimeUtils.timestampToDate(systemRoleDTO.getCreateTime()));
            systemRoleDTO.setUpdateTime(TimeUtils.timestampToDate(systemRoleDTO.getUpdateTime()));
            systemRoleDTO.setMember(0);
            systemRoleDTO.setMenuList(Collections.EMPTY_LIST);

        });

        return list;
    }

    /**
     * 角色列表
     *
     * @author fzr
     * @param pageValidate 参数
     * @return PageResult<SysRoleListVo>
     */
    @Override
    public PageResult<SystemRoleDTO> list(@Validated PageValidate pageValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        QueryWrapper<SystemAuthRole> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));

        IPage<SystemAuthRole> iPage = systemAuthRoleMapper.selectPage(new Page<>(page, limit), queryWrapper);

        List<SystemRoleDTO> list = DozerUtils.mapList(iPage.getRecords(), SystemRoleDTO.class);
        list.stream().forEach(systemRoleDTO -> {
            List<Integer> ids = userBasicMapper.selectChildrenById(systemRoleDTO.getId());
            Integer member = ids.size();

            systemRoleDTO.setMenuList(new ArrayList<>());
            systemRoleDTO.setMember(member);
            systemRoleDTO.setCreateTime(TimeUtils.timestampToDate(systemRoleDTO.getCreateTime()));
            systemRoleDTO.setUpdateTime(TimeUtils.timestampToDate(systemRoleDTO.getUpdateTime()));
        });
        return PageResult.iPageHandle(iPage.getTotal(), iPage.getCurrent(), iPage.getSize(), list);
    }

    /**
     * 角色详情
     *
     * @author fzr
     * @param id 主键参数
     * @return SysRole
     */
    @Override
    public SystemRoleDTO detail(Long id) {
        SystemAuthRole systemAuthRole = systemRoleRepo.queryListByCondition(id, null, null);
        Assert.notNull(systemAuthRole, "角色已不存在!");
        SystemRoleDTO dto = DozerUtils.map(systemAuthRole, SystemRoleDTO.class);
        dto.setMember(0);
        dto.setMenuList(iSystemAuthPermService.selectMenuIdsByRoleId(Lists.newArrayList(systemAuthRole.getId())));
        dto.setCreateTime(TimeUtils.timestampToDate(systemAuthRole.getCreateTime()));
        dto.setUpdateTime(TimeUtils.timestampToDate(systemAuthRole.getUpdateTime()));

        return dto;
    }

    /**
     * 新增角色
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    @Transactional
    public void add(SystemRoleCreateValidate createValidate) {
        String[] fields = new String[] {"id", "name"};
        Assert.isNull(systemRoleRepo.queryListByCondition(null, createValidate.getName(), fields), "角色名称已存在!");
        SystemAuthRole model = DozerUtils.map(createValidate, SystemAuthRole.class);
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthRoleMapper.insert(model);
        iSystemAuthPermService.batchSaveByMenuIds(model.getId(), createValidate.getMenuIds());
    }

    /**
     * 编辑角色
     *
     * @author fzr
     * @param updateValidate 参数
     */
    @Override
    @Transactional
    public void edit(SystemRoleUpdateValidate updateValidate) {
        String[] fields = new String[] {"id", "name"};
        SystemAuthRole systemAuthRole = systemRoleRepo.queryListByCondition(updateValidate.getId().longValue(), null, fields);
        Assert.notNull(systemAuthRole, "角色已不存在!");

        SystemAuthRole existNameRole = systemRoleRepo.queryListByCondition(null, updateValidate.getName(), fields);
        Assert.isTrue(existNameRole.getId() == updateValidate.getId().longValue() && existNameRole.getName().equals(updateValidate.getName()), "角色名称已存在!");
        SystemAuthRole model = DozerUtils.map(updateValidate, SystemAuthRole.class);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthRoleMapper.updateById(model);

        iSystemAuthPermService.batchDeleteByRoleId(updateValidate.getId().longValue());
        iSystemAuthPermService.batchSaveByMenuIds(updateValidate.getId().longValue(), updateValidate.getMenuIds());
        RedisUtils.del(AdminConfig.backstageRolesKey);
    }

    /**
     * 删除角色
     *
     * @author fzr
     * @param id 主键参数
     */
    @Override
    @Transactional
    public void del(Long id) {
        String[] fields = new String[] {"id", "name"};

        Assert.notNull(systemRoleRepo.queryListByCondition(id, null, fields),
                "角色已不存在!");

//        Assert.isNull(systemAuthAdminMapper.selectOne(new QueryWrapper<SystemAuthAdmin>()
//                .select("id", "role", "nickname")
//                .eq("role", id)
//                .eq("is_delete", 0)),
//                "角色已被管理员使用,请先移除");

        systemAuthRoleMapper.deleteById(id);
        iSystemAuthPermService.batchDeleteByRoleId(id);
        RedisUtils.del(AdminConfig.backstageRolesKey);
    }

}
