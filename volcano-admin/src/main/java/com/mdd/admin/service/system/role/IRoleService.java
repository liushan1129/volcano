package com.mdd.admin.service.system.role;

import com.mdd.admin.dto.system.role.SystemRoleDTO;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemRoleCreateValidate;
import com.mdd.admin.validate.system.SystemRoleUpdateValidate;
import com.mdd.common.core.PageResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 系统角色服务接口类
 */
public interface IRoleService {

    /**
     * 角色所有
     *
     * @author fzr
     * @return List<SystemAuthRoleVo>
     */
    List<SystemRoleDTO> all();

    /**
     * 角色列表
     *
     * @author fzr
     * @param pageValidate 参数
     * @return PageResult<SysRoleListVo>
     */
    PageResult<SystemRoleDTO> list(@Validated PageValidate pageValidate);

    /**
     * 角色详情
     *
     * @author fzr
     * @param id 主键参数
     * @return SysRole
     */
    SystemRoleDTO detail(Long id);

    /**
     * 角色新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    void add(SystemRoleCreateValidate createValidate);

    /**
     * 角色更新
     *
     * @author fzr
     * @param updateValidate 参数
     */
    void edit(SystemRoleUpdateValidate updateValidate);

    /**
     * 角色删除
     *
     * @author fzr
     * @param id 主键参数
     */
    void del(Long id);

}
