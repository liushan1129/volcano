package com.mdd.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdd.admin.dto.group.GroupDTO;
import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.group.Group;

/**
 * <p>
 * 小组表 服务类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
public interface IGroupService extends IService<Group> {
    void add(GroupCreateValidate createValidate);

    PageResult<GroupDTO> list(PageValidate pageValidate, GroupSearchValidate searchValidate);

    Object detail(Long id);

    void edit(GroupUpdateValidate updateValidate);

    void del(long longValue);

}
