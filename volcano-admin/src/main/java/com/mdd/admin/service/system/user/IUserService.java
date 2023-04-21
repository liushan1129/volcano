package com.mdd.admin.service.system.user;

import com.mdd.admin.dto.system.user.SystemUserDetailsDTO;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.dto.system.user.SystemUserSelvesDTO;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.*;
import com.mdd.admin.validate.system.condition.SystemUserQueryCondition;
import com.mdd.common.core.PageResult;

import java.util.List;

/**
 * 系统管理员服务接口类
 */
public interface IUserService {

    /**
     * 管理员列表
     *
     * @author fzr
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<SystemAuthAdminListedVo>
     */
    PageResult<SystemUserBasicListDTO> list(PageValidate pageValidate, SystemUserSearchValidate searchValidate);

    /**
     * 当前管理员
     *
     * @author fzr
     * @return SystemSelfVo
     */
    SystemUserSelvesDTO self(Long adminId);

    /**
     * 管理员详情
     *
     * @author fzr
     * @param id 主键参数
     * @return SystemAuthAdminDetailVo
     */
    SystemUserDetailsDTO detail(Long id);

    /**
     * 管理员新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    void add(SystemUserCreateValidate createValidate);

    /**
     * 管理员编辑
     *
     * @author fzr
     * @param updateValidate 参数
     * @param roleIds 当前登录用户角色
     */
    void edit(SystemUserUpdateValidate updateValidate, List<Long> roleIds);

    /**
     * 当前管理员更新
     *
     * @author fzr
     * @param upInfoValidate 参数
     * @param adminId 管理员ID
     */
    void upInfo(SystemUserUpInfoValidate upInfoValidate, Long adminId);

    /**
     * 管理员删除
     *
     * @author fzr
     * @param id 主键参数
     * @param roleIds 管理员ID
     * @param loginId
     */
    void del(Long id, List<Long> roleIds, Long loginId);

    /**
     * 管理员状态切换
     *
     * @author fzr
     * @param id 主键参数
     */
    void disable(Long id, Long adminId);

    /**
     * 缓存管理员
     */
    void cacheAdminUserByUid(Long id);

    List<SystemUserBasicListDTO> listByCondition(SystemUserQueryCondition queryCondition);
}
