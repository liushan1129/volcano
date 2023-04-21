package com.mdd.admin.service.system.user.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.mdd.admin.config.AdminConfig;
import com.mdd.admin.dto.system.user.SystemUserDetailsDTO;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.dto.system.user.SystemUserSelvesDTO;
import com.mdd.admin.service.system.user.IUserService;
import com.mdd.admin.service.ISystemAuthPermService;
import com.mdd.admin.repo.SystemDeptRepo;
import com.mdd.admin.repo.SystemMenuRepo;
import com.mdd.admin.repo.SystemRoleRepo;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.*;
import com.mdd.admin.validate.system.condition.SystemUserQueryCondition;
import com.mdd.common.core.PageResult;
import com.mdd.common.entity.system.SystemAuthDept;
import com.mdd.common.entity.system.SystemAuthMenu;
import com.mdd.common.entity.system.SystemAuthRole;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.enums.SpecialRoleEnum;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.system.*;
import com.mdd.admin.repo.SystemUserRepo;
import com.mdd.common.util.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统管理员服务实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private SystemUserBasicMapper userBasicMapper;

    @Resource
    private SystemUserRepo userBasicRepo;

    @Resource
    private SystemRoleRepo roleRepo;

    @Resource
    private SystemDeptRepo deptRepo;

    @Resource
    private SystemMenuRepo menuRepo;

    @Resource
    private ISystemAuthPermService iSystemAuthPermService;

    private static final Integer SALT_RANDOM = 5;
    private static  final String DEFAULT_AVATAR = "/api/static/backend_avatar.png";

    /**
     * 管理员列表
     *
     * @author fzr
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return PageResult<SystemUserBasicListDTO>
     */
    @Override
    public PageResult<SystemUserBasicListDTO> list(PageValidate pageValidate, SystemUserSearchValidate searchValidate) {
        IPage<SystemUserBasicListDTO> iPage = userBasicRepo.queryList(pageValidate, searchValidate);
        List<SystemAuthRole> allRoles = roleRepo.queryAllRole(null);
        Map<Long, String> roleMap = allRoles.stream().collect(Collectors.toMap(SystemAuthRole :: getId, SystemAuthRole::getName));
        List<SystemAuthDept> deptList = deptRepo.queryAllDept();
        Map<Long, String> deptMap = deptList.stream().collect(Collectors.toMap(SystemAuthDept :: getId, SystemAuthDept::getName));

        for (SystemUserBasicListDTO dto : iPage.getRecords()) {
            dto.setGenderStr(dto.getGender());
            List<Long> roleIds = ArrayUtils.stringToListAsLong(dto.getRoleIds(), ",");
            if (roleIds.size() == 1 && SpecialRoleEnum.SUPER_ADMIN.getCode() == roleIds.get(0)) {
                dto.setRoleName(SpecialRoleEnum.SUPER_ADMIN.getName());
            } else {
                List<String> roleNameList = new LinkedList<>();
                if (!roleIds.isEmpty()) {
                    roleNameList = roleIds.stream().map(roleId -> roleMap.get(roleId)).collect(Collectors.toList());
                }
                dto.setRoleName(ArrayUtils.listToStringByStr(roleNameList, "/"));
            }

            if (StringUtils.isBlank(dto.getDeptIds())) {
                dto.setDeptName("");
            } else {
                List<String>  deptNameList= new LinkedList<>();
                List<Long> deptIds = ArrayUtils.stringToListAsLong(dto.getDeptIds(), ",");
                if (!deptIds.isEmpty()) {
                    deptNameList= deptIds.stream().map(deptId -> deptMap.get(deptId)).collect(Collectors.toList());
                }
                dto.setDeptName(ArrayUtils.listToStringByStr(deptNameList, "/"));
            }
            dto.setCreateTime(TimeUtils.timestampToDate(dto.getCreateTime()));
            dto.setUpdateTime(TimeUtils.timestampToDate(dto.getUpdateTime()));
            dto.setLastLoginTime(TimeUtils.timestampToDate(dto.getLastLoginTime()));
        }

        return PageResult.iPageHandle(iPage);
    }

    /**
     * 当前管理员
     *
     * @author fzr
     * @param adminId 管理员ID
     * @return SystemUserSelvesDTO
     */
    @Override
    public SystemUserSelvesDTO self(Long adminId) {
        // 管理员信息
        UserBasic userBasic = userBasicRepo.queryById(adminId);
        SystemUserDetailsDTO userDetailsDTO = DozerUtils.map(userBasic, SystemUserDetailsDTO.class);
        buildUserDetailsOtherProperties(userDetailsDTO, userBasic);

        // 角色权限
        List<String> auths = new LinkedList<>();
        List<Long> roleIds = ArrayUtils.stringToListAsLong(userBasic.getRoleIds(), ",");
        if (!roleIds.contains(SpecialRoleEnum.SUPER_ADMIN.getCode())) {
            List<Long> menuIds = iSystemAuthPermService.selectMenuIdsByRoleId(roleIds);
            if (menuIds.size() > 0) {
                List<SystemAuthMenu> systemAuthMenus = menuRepo.queryByIdAndType(menuIds, Arrays.asList("C", "A"));
                // 处理权限
                auths = systemAuthMenus.stream().filter(menu -> StringUtils.isNotNull(menu.getPerms()) && StringUtils.isNotEmpty(menu.getPerms())).map(SystemAuthMenu::getPerms).collect(Collectors.toList());
                for (SystemAuthMenu item : systemAuthMenus) {
                    if (StringUtils.isNotNull(item.getPerms()) && StringUtils.isNotEmpty(item.getPerms())) {
                        auths.add(item.getPerms().trim());
                    }
                }
            }
            // 没有权限
            if (auths.size() <= 0) {
                auths.add("");
            }
        } else {
            // 所有权限
            auths.add("*");
        }

        // 返回数据
        SystemUserSelvesDTO selvesDTO = new SystemUserSelvesDTO();
        selvesDTO.setUser(userDetailsDTO);
        selvesDTO.setPermissions(auths);
        return selvesDTO;
    }

    private void buildUserDetailsOtherProperties(SystemUserDetailsDTO userDetailsDTO, UserBasic userBasic) {
        userDetailsDTO.setRoleIdList(ArrayUtils.stringToListAsLong(userBasic.getRoleIds(), ","));
        userDetailsDTO.setDeptIdList(StringUtils.isBlank(userBasic.getDeptIds()) ? new ArrayList<>() : ArrayUtils.stringToListAsLong(userBasic.getDeptIds(), ","));
        userDetailsDTO.setPostIdList(StringUtils.isBlank(userBasic.getPostIds()) ? new ArrayList<>() : ArrayUtils.stringToListAsLong(userBasic.getPostIds(), ","));
        userDetailsDTO.setAvatar(UrlUtils.toAbsoluteUrl(userBasic.getAvatar()));
        userDetailsDTO.setUpdateTime(TimeUtils.timestampToDate(userBasic.getUpdateTime()));
        userDetailsDTO.setCreateTime(TimeUtils.timestampToDate(userBasic.getCreateTime()));
        userDetailsDTO.setLastLoginTime(TimeUtils.timestampToDate(userBasic.getLastLoginTime()));
    }

    /**
     * 管理员详细
     *
     * @author fzr
     * @param id 主键
     * @return SystemAuthAdminDetailVo
     */
    @Override
    public SystemUserDetailsDTO detail(Long id) {
        UserBasic userBasic = userBasicRepo.queryById(id);
        Assert.notNull(userBasic, "账号已不存在！");
        SystemUserDetailsDTO dto = DozerUtils.map(userBasic, SystemUserDetailsDTO.class);
        buildUserDetailsOtherProperties(dto, userBasic);
        return dto;
    }

    /**
     * 管理员新增
     *
     * @author fzr
     * @param createValidate 参数
     */
    @Override
    public void add(SystemUserCreateValidate createValidate) {
        String[] field = {"id", "username", "nickname"};
        Assert.isNull(userBasicRepo.queryFieldByCondition(null, createValidate.getUsername(), null, field), "账号已存在换一个吧！");
        Assert.isNull(userBasicRepo.queryFieldByCondition(null, null, createValidate.getNickname(), field), "昵称已存在换一个吧！");

        String salt = ToolsUtils.randomString(SALT_RANDOM);
        String pwd  = ToolsUtils.makeMd5(createValidate.getPassword().trim() + salt);

        String createAvatar  = createValidate.getAvatar();
        String defaultAvatar = DEFAULT_AVATAR;
        String avatar = StringUtils.isNotEmpty(createValidate.getAvatar()) ? UrlUtils.toRelativeUrl(createAvatar) : defaultAvatar;

        UserBasic model = DozerUtils.map(createValidate, UserBasic.class);
        model.setRoleIds(ArrayUtils.listToStringByLong(createValidate.getRoleIdList(), ","));
        model.setDeptIds(ArrayUtils.listToStringByLong(createValidate.getDeptIdList(), ","));
        model.setPostIds(ArrayUtils.listToStringByLong(createValidate.getPostIdList(), ","));
        model.setAvatar(avatar);
        model.setPassword(pwd);
        model.setSalt(salt);
        model.setCreateTime(System.currentTimeMillis() / 1000);
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        userBasicMapper.insert(model);

    }

    /**2
     * 管理员更新
     *
     * @author fzr
     * @param updateValidate 参数
     * @param roleIds 当前登录用户角色
     */
    @Override
    public void edit(SystemUserUpdateValidate updateValidate, List<Long> roleIds) {
        String[] field = {"id", "username", "nickname", "role_ids"};
        Long userId = updateValidate.getId().longValue();
        UserBasic userBasic = userBasicRepo.queryFieldByCondition(userId, null, null, field);
        Assert.notNull(userBasic, "账号不存在了!");
        List<Long> userRoleIds = ArrayUtils.stringToListAsLong(userBasic.getRoleIds(), ",");
        if(!roleIds.isEmpty() && userRoleIds.contains(SpecialRoleEnum.SUPER_ADMIN.getCode())) {
            throw new OperateException("超级管理员信息不可修改!");
        }
        if(updateValidate.getId() != null && updateValidate.getId().longValue() != userBasic.getId()) {
            Assert.isNull(userBasicRepo.queryFieldByCondition(null, updateValidate.getUsername(), null, field)
                    , "账号已存在换一个吧!");
            Assert.isNull(userBasicRepo.queryFieldByCondition(null, null, updateValidate.getNickname(), field)
                    , "昵称已存在换一个吧!");
        }
        UserBasic model = DozerUtils.map(updateValidate, UserBasic.class);
        model.setRoleIds(ArrayUtils.listToStringByLong(updateValidate.getRoleIdList(), ","));
        model.setDeptIds(ArrayUtils.listToStringByLong(updateValidate.getDeptIdList(), ","));
        model.setPostIds(ArrayUtils.listToStringByLong(updateValidate.getPostIdList(), ","));
        model.setUpdateTime(System.currentTimeMillis() / 1000);
        if (StringUtils.isNotNull(updateValidate.getPassword()) && StringUtils.isNotEmpty(updateValidate.getPassword())) {
            String salt = ToolsUtils.randomString(SALT_RANDOM);
            String pwd = ToolsUtils.makeMd5( updateValidate.getPassword().trim() + salt);
            model.setPassword(pwd);
            model.setSalt(salt);
        } else {
            model.setPassword(userBasic.getPassword());
            model.setSalt(userBasic.getSalt());
        }
        userBasicMapper.updateById(model);
        this.cacheAdminUserByUid(updateValidate.getId().longValue());
        if (StringUtils.isNotNull(updateValidate.getPassword()) && StringUtils.isNotEmpty(updateValidate.getPassword())) {
            StpUtil.kickout(updateValidate.getId());
        }
    }

    /**
     * 当前管理员更新
     *
     * @author fzr
     * @param upInfoValidate 参数
     */
    @Override
    public void upInfo(SystemUserUpInfoValidate upInfoValidate, Long adminId) {
        String[] field = {"id", "username", "nickname"};

        UserBasic userBasic = userBasicRepo.queryFieldByCondition(adminId, null, null, field);
        Assert.notNull(userBasic, "账号不存在了!");

        String createAvatar  = upInfoValidate.getAvatar();
        String defaultAvatar = DEFAULT_AVATAR;
        String avatar = StringUtils.isNotEmpty(upInfoValidate.getAvatar()) ? UrlUtils.toRelativeUrl(createAvatar) : defaultAvatar;

        userBasic.setAvatar(avatar);
        userBasic.setNickname(upInfoValidate.getNickname());
        userBasic.setUpdateTime(System.currentTimeMillis() / 1000);

        if (StringUtils.isNotNull(upInfoValidate.getPassword()) && StringUtils.isNotEmpty(upInfoValidate.getPassword())) {
            String currPassword = ToolsUtils.makeMd5(upInfoValidate.getCurrPassword() + userBasic.getSalt());
            Assert.isFalse(!currPassword.equals(userBasic.getPassword()), "当前密码不正确!");
            String salt = ToolsUtils.randomString(SALT_RANDOM);
            String pwd = ToolsUtils.makeMd5( upInfoValidate.getPassword().trim() + salt);
            userBasic.setPassword(pwd);
            userBasic.setSalt(salt);
        }

        userBasicMapper.updateById(userBasic);
        this.cacheAdminUserByUid(adminId);

        if (StringUtils.isNotNull(upInfoValidate.getPassword()) && StringUtils.isNotEmpty(upInfoValidate.getPassword())) {
            StpUtil.kickout(adminId);
        }
    }

    /**
     * 管理员删除
     *
     * @author fzr
     * @param id 主键
     * @param roleIds 管理员ID
     * @param loginId
     */
    @Override
    public void del(Long id, List<Long> roleIds, Long loginId) {
        String[] field = {"id", "username", "nickname"};
        UserBasic userBasic = userBasicRepo.queryFieldByCondition(id, null, null, field);
        Assert.notNull(userBasic, "账号已不存在!");
        Assert.isFalse(!CollectionUtils.isEmpty(roleIds), "超级管理员不允许删除!");
        Assert.isTrue(SpecialRoleEnum.SUPER_ADMIN.getCode() == Long.valueOf(userBasic.getRoleIds()), "超级管理员不允许删除!");
        Assert.isFalse(id == loginId , "不能删除自己!");
        UserBasic model = new UserBasic();
        model.setId(id);
        //1已删除，0 未删除
        model.setIsDelete(1);
        model.setDeleteTime(System.currentTimeMillis() / 1000);
        userBasicMapper.updateById(model);
        this.cacheAdminUserByUid(id);

        StpUtil.kickout(id);
    }

    /**
     * 管理员状态切换
     *
     * @author fzr
     * @param id 主键参数
     * @param adminId 管理员ID
     */
    @Override
    public void disable(Long id, Long adminId) {
        String[] field = {"id", "username", "nickname", "is_disable"};

        UserBasic vaUserBasic = userBasicRepo.queryFieldByCondition(id, null, null, field);
        Assert.notNull(vaUserBasic, "账号已不存在!");
        Assert.isFalse(id.equals(adminId) , "不能禁用自己!");

        //0：启用，1：禁用
        Integer disable = vaUserBasic.getIsDisable() == 1 ? 0 : 1;
        vaUserBasic.setIsDisable(disable);
        vaUserBasic.setUpdateTime(TimeUtils.timestamp());
        userBasicMapper.updateById(vaUserBasic);
        this.cacheAdminUserByUid(id);
        if (disable.equals(1)) {
            StpUtil.kickout(id);
        }
    }

    /**
     * 缓存管理员
     */
    @Override
    public void cacheAdminUserByUid(Long id) {
        String[] field = {"id", "role_ids", "username", "nickname", "is_multipoint", "is_disable", "is_delete"};
        UserBasic userBasic = userBasicRepo.queryFieldByCondition(id, null, null, field);

        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", userBasic.getId());
        user.put("roleIds", userBasic.getRoleIds());
        user.put("username", userBasic.getUsername());
        user.put("nickname", userBasic.getNickname());
        user.put("isMultipoint", userBasic.getIsMultipoint());
        user.put("isDisable", userBasic.getIsDisable());
        user.put("isDelete", userBasic.getIsDelete());

        Map<String, Object> map  = new LinkedHashMap<>();
        map.put(String.valueOf(userBasic.getId()), JSON.toJSONString(user));

        RedisUtils.hmSet(AdminConfig.backstageManageKey, map);
    }

    @Override
    public List<SystemUserBasicListDTO> listByCondition(SystemUserQueryCondition queryCondition) {
        List<UserBasic> userBasicList = userBasicRepo.queryListByCondition(queryCondition);
        List<SystemUserBasicListDTO> dtos = DozerUtils.mapList(userBasicList, SystemUserBasicListDTO.class);
        return dtos;
    }

}
