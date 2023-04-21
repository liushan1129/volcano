package com.mdd.admin.service;

import com.mdd.admin.dto.member.MemberDTO;
import com.mdd.admin.validate.CourseTypeSearchValidate;
import com.mdd.admin.validate.MemberCreateValidate;
import com.mdd.admin.validate.MemberSearchValidate;
import com.mdd.admin.validate.MemberUpdateValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.condition.MemberQueryCondition;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.member.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
public interface IMemberService extends IService<Member> {

    void add(MemberCreateValidate createValidate);

    PageResult<MemberDTO> list(PageValidate pageValidate, MemberSearchValidate searchValidate);

    Object detail(Long id);

    void edit(MemberUpdateValidate updateValidate);

    void del(long longValue);

    List<MemberDTO> listByCondition(MemberQueryCondition queryCondition);
}
