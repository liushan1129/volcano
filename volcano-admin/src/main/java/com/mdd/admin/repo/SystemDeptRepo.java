package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.admin.validate.system.SystemDeptSearchValidate;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.mapper.system.SystemAuthDeptMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 22:19
 */
@Component
public class SystemDeptRepo {
    @Resource
    private SystemAuthDeptMapper systemAuthDeptMapper;

    public List<SystemAuthDept> queryListByIds(List<Long> deptIds) {
        List<SystemAuthDept> deptList = systemAuthDeptMapper.selectList(new QueryWrapper<SystemAuthDept>()
                .select("id,name")
                .in("id", deptIds)
                .eq("is_delete", 0));
        return deptList;
    }

    public List<SystemAuthDept> queryAllDept() {
        return systemAuthDeptMapper.selectList(new QueryWrapper<SystemAuthDept>().eq("is_delete", 0));

    }

    public List<SystemAuthDept> queryByPid(Long pid) {
        QueryWrapper<SystemAuthDept> queryWrapper = new QueryWrapper<SystemAuthDept>()
                .gt("pid", 0)
                .eq("is_delete", 0);
        //                .orderByDesc((Arrays.asList("sort", "id"))));
        return systemAuthDeptMapper.selectList(queryWrapper);
    }

    public List<SystemAuthDept> queryList(SystemDeptSearchValidate searchValidate) {
        QueryWrapper<SystemAuthDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
//        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));
        queryWrapper.select(SystemAuthDept.class, info ->
                !info.getColumn().equals("is_delete") &&
                        !info.getColumn().equals("delete_time"));

        systemAuthDeptMapper.setSearch(queryWrapper, searchValidate, new String[]{
                "like:name:str",
                "=:isStop@is_stop:int"
        });
        return systemAuthDeptMapper.selectList(queryWrapper);

    }

    public SystemAuthDept queryById(Long id) {
       return systemAuthDeptMapper.selectOne(
                new QueryWrapper<SystemAuthDept>()
                        .select(SystemAuthDept.class, info ->
                                !info.getColumn().equals("is_delete") &&
                                        !info.getColumn().equals("delete_time"))
                        .eq("id", id)
                        .eq("is_delete", 0)
                        .last("limit 1"));
    }

    public SystemAuthDept queryFieldsByCondition(Long id, Long pid, String[] fields) {
        QueryWrapper<SystemAuthDept> queryWrapper = new QueryWrapper<SystemAuthDept>()
                .select(fields);
                if(id != null) {
                    queryWrapper.eq("id", id);
                }
                if(pid != null) {
                    queryWrapper.eq("pid", pid);
                }

                queryWrapper.eq("is_delete", 0)
                .last("limit 1");
        return systemAuthDeptMapper.selectOne(queryWrapper);

    }
}
