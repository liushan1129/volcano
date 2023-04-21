package com.mdd.common.mapper.member;

import com.mdd.common.core.basics.IBaseMapper;
import com.mdd.common.entity.member.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Mapper
public interface MemberMapper extends IBaseMapper<Member> {

}
