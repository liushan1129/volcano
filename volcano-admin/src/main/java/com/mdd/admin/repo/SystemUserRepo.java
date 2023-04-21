package com.mdd.admin.repo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemUserSearchValidate;
import com.mdd.admin.validate.system.condition.SystemUserQueryCondition;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.mapper.system.SystemUserBasicMapper;
import com.mdd.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/1 22:05
 */
@Component
public class SystemUserRepo {

    @Resource
    SystemUserBasicMapper userBasicMapper;
    public List<UserBasic> all() {
        QueryWrapper queryWrapper = new QueryWrapper<UserBasic>().eq("is_delete", 0);
        return userBasicMapper.selectList(queryWrapper);
    }

    public IPage<SystemUserBasicListDTO> queryList(PageValidate pageValidate, SystemUserSearchValidate searchValidate) {
        Integer page  = pageValidate.getPageNo();
        Integer limit = pageValidate.getPageSize();

        MPJQueryWrapper<UserBasic> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.select("t.id, t.role_ids, t.dept_ids, t.post_ids, t.username, t.nickname, t.gender,"
                        + "t.age, t.weight, t.height, t.is_multipoint, t.is_disable, t.last_login_ip, "
                        + "t.last_login_time, t.create_time, t.update_time")
                .eq("t.is_delete", 0)
                .orderByDesc(Arrays.asList("t.id", "t.sort"));

        userBasicMapper.setSearch(mpjQueryWrapper, searchValidate, new String[]{
                "like:username:str",
                "like:enUsername:str",
                "like:nickname:str"
        });

        if (StringUtils.isNotNull(searchValidate.getRole())) {
            mpjQueryWrapper.in("role_ids", Collections.singletonList(searchValidate.getRole()));
        }

        IPage<SystemUserBasicListDTO> iPage = userBasicMapper.selectJoinPage(
                new Page<>(page, limit),
                SystemUserBasicListDTO.class,
                mpjQueryWrapper);
        return iPage;
    }

    public UserBasic queryById(Long adminId) {
        UserBasic userBasic = userBasicMapper.selectOne(new QueryWrapper<UserBasic>()
                .select(UserBasic.class, info->
                        !info.getColumn().equals("salt") &&
                                !info.getColumn().equals("password") &&
                                !info.getColumn().equals("is_delete") &&
                                !info.getColumn().equals("delete_time"))
                .eq("is_delete", 0)
                .eq("id", adminId)
                .last("limit 1"));
        return userBasic;
    }

    public UserBasic queryFieldByCondition(Long id, String username, String nickname, String[] field) {
        QueryWrapper queryWrapper = new QueryWrapper<UserBasic>();
        if(field != null) {
            queryWrapper.select(field);
        } else {
            queryWrapper.select();
        }
        queryWrapper.eq("is_delete", 0);
        if(id != null) {
            queryWrapper.eq("id", id);

        }
        if(Strings.isNotBlank(username)) {
            queryWrapper.eq("username", username);
        }
        if (Strings.isNotBlank(nickname)) {
            queryWrapper.eq("nickname", nickname);
        }
        queryWrapper.last("limit 1");
        return userBasicMapper.selectOne(queryWrapper);
    }

    public List<UserBasic> queryListByCondition(SystemUserQueryCondition queryCondition) {
        QueryWrapper queryWrapper = new QueryWrapper<UserBasic>()
                .select()
                .eq("is_delete", 0);
        if(queryCondition.getId() != null) {
            queryWrapper.eq("id", queryCondition.getId());

        }
        if(Strings.isNotBlank(queryCondition.getUsername())) {
            queryWrapper.like("username", queryCondition.getUsername());
        }
        if (Strings.isNotBlank(queryCondition.getNickname())) {
            queryWrapper.like("nickname", queryCondition.getNickname());
        }
        return userBasicMapper.selectList(queryWrapper);
    }


}
