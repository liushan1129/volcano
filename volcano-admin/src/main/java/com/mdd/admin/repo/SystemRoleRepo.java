package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdd.common.entity.system.SystemAuthRole;
import com.mdd.common.mapper.system.SystemAuthRoleMapper;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Arrays;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 22:19
 */
@Component
public class SystemRoleRepo {
    @Resource
    private SystemAuthRoleMapper systemAuthRoleMapper;

    public List<SystemAuthRole> queryListByIds(List<Long> roleIds) {
        List<SystemAuthRole> roleList = systemAuthRoleMapper.selectList(new QueryWrapper<SystemAuthRole>()
                .select("id, name")
                .in("id", roleIds));
        return roleList;
    }

    public List<SystemAuthRole> queryEnableRole() {
        return systemAuthRoleMapper.selectList(new QueryWrapper<SystemAuthRole>().eq("is_disable", 0));
    }

    public List<SystemAuthRole> queryAllRole(String[] fields) {
        QueryWrapper<SystemAuthRole> queryWrapper = new QueryWrapper<>();
        if(Arrays.isNullOrEmpty(fields)) {
            queryWrapper.select();
        }else {
            queryWrapper.select(fields);
        }
//        queryWrapper.orderByDesc(Arrays.asList("sort", "id"));
        return systemAuthRoleMapper.selectList(queryWrapper);

    }

    public SystemAuthRole queryListByCondition(Long id, String name, String[] fields) {
        QueryWrapper<SystemAuthRole> queryWrapper = new QueryWrapper<>();
        if(Arrays.isNullOrEmpty(fields)) {
            queryWrapper.select();
        } else {
            queryWrapper.select(fields);
        }
        if(id != null) {
            queryWrapper.eq("id", id);
        }
        if(Strings.isNotBlank(name)) {
            queryWrapper.eq("name", name.trim());
        }
        queryWrapper.last("limit 1");
       return systemAuthRoleMapper.selectOne(queryWrapper);
    }
}
