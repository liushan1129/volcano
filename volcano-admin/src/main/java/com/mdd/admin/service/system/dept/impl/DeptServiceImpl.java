package com.mdd.admin.service.system.dept.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.dto.system.dept.SystemDeptDTO;
import com.mdd.admin.service.system.dept.IDeptService;
import com.mdd.admin.repo.SystemDeptRepo;
import com.mdd.admin.validate.system.SystemDeptCreateValidate;
import com.mdd.admin.validate.system.SystemDeptSearchValidate;
import com.mdd.admin.validate.system.SystemDeptUpdateValidate;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.mapper.system.SystemAuthDeptMapper;
import com.mdd.common.mapper.system.SystemUserBasicMapper;
import com.mdd.common.util.ArrayUtils;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统部门服务实现类
 */
@Service
class DeptServiceImpl implements IDeptService {

    @Resource
    SystemAuthDeptMapper systemAuthDeptMapper;

    @Resource
    SystemUserBasicMapper systemAuthAdminMapper;
    @Resource
    SystemDeptRepo deptRepo;

    /**
     * 岗位所有
     *
     * @author fzr
     * @return List<SystemAuthDeptVo>
     */
    @Override
    public List<SystemDeptDTO> all() {
        List<SystemAuthDept> systemAuthDeptList = deptRepo.queryByPid(0L);
        List<SystemDeptDTO> list = DozerUtils.mapList(systemAuthDeptList, SystemDeptDTO.class);
        list.stream().forEach(systemAuthDeptDTO -> {
            systemAuthDeptDTO.setUpdateTime(TimeUtils.timestampToDate(systemAuthDeptDTO.getUpdateTime()));
            systemAuthDeptDTO.setCreateTime(TimeUtils.timestampToDate(systemAuthDeptDTO.getCreateTime()));

        });
        return list;
    }

    /**
     *  部门列表
     *
     * @author fzr
     * @param searchValidate 搜索参数
     * @return JSONArray
     */
    @Override
    public JSONArray list(SystemDeptSearchValidate searchValidate) {
        List<SystemAuthDept> systemAuthDeptList = deptRepo.queryList(searchValidate);
        List<SystemDeptDTO> list = DozerUtils.mapList(systemAuthDeptList, SystemDeptDTO.class);
        list.stream().forEach(dto -> {
            dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
            dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
        });
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(list));
        return ArrayUtils.listToTree(jsonArray, "id", "pid", "children");
    }

    /**
     * 部门详情
     *
     * @author fzr
     * @param id 主键
     * @return SystemDeptVo
     */
    @Override
    public SystemDeptDTO detail(Long id) {
        SystemAuthDept systemAuthDept = deptRepo.queryById(id);
        Assert.notNull(systemAuthDept, "部门已不存在!");

        SystemDeptDTO dto  = DozerUtils.map(systemAuthDept, SystemDeptDTO.class);
        dto.setCreateTime(TimeUtils.timestampToDate(systemAuthDept.getCreateTime()));
        dto.setUpdateTime(TimeUtils.timestampToDate(systemAuthDept.getUpdateTime()));

        return dto;
    }

    /**
     * 部门新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    public void add(SystemDeptCreateValidate createValidate) {
        if (createValidate.getPid().equals(0)) {
            String[] fields = new String[]{"id","pid","name"};
            SystemAuthDept systemAuthDept = deptRepo.queryFieldsByCondition(null, 0L, fields);
            Assert.isNull(systemAuthDept, "顶级部门只允许有一个");
        }
        SystemAuthDept model = DozerUtils.map(createValidate, SystemAuthDept.class);
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.insert(model);
    }

    /**
     * 部门编辑
     *
     * @author fzr
     * @param updateValidate 参数
     */
    @Override
    public void edit(SystemDeptUpdateValidate updateValidate) {
        SystemAuthDept model = deptRepo.queryById(updateValidate.getId().longValue());
        Assert.notNull(model, "部门不存在");
        Assert.isFalse((model.getPid().equals(0) && updateValidate.getPid() > 0), "顶级部门不能修改上级");
        Assert.isFalse(updateValidate.getId().equals(updateValidate.getPid()), "上级部门不能是自己");
        model = DozerUtils.map(updateValidate, SystemAuthDept.class);
//        model.setPid(updateValidate.getPid());
//        model.setName(updateValidate.getName());
//        model.setDuty(updateValidate.getDuty());
//        model.setMobile(updateValidate.getMobile());
//        model.setSort(updateValidate.getSort());
//        model.setIsStop(updateValidate.getIsStop());
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.updateById(model);
    }

    /**
     * 部门删除
     *
     * @author fzr
     * @param id 主键
     */
    @Override
    public void del(Long id) {
        String[] fields = new String[]{"id","pid","name"};
        SystemAuthDept model = deptRepo.queryFieldsByCondition(id, null, fields);

        Assert.notNull(model, "部门不存在");
        Assert.isFalse((model.getPid() == 0), "顶级部门不能删除");

        SystemAuthDept pModel = deptRepo.queryFieldsByCondition(null, id, fields);

        Assert.isNull(pModel, "请先删除子级部门");

//        SystemAuthAdmin systemAuthAdmin = systemAuthAdminMapper.selectOne(new QueryWrapper<SystemAuthAdmin>()
//                .select("id,nickname")
//                .eq("dept_id", id)
//                .eq("is_delete", 0)
//                .last("limit 1"));

//        Assert.isNull(systemAuthAdmin, "该部门已被管理员使用,请先移除");

        model.setIsDelete(1);
        model.setDeleteTime(System.currentTimeMillis() / 1000);
        systemAuthDeptMapper.updateById(model);
    }

}