package com.mdd.admin;

import com.google.common.collect.Lists;
import com.mdd.admin.dto.system.user.SystemUserBasicListDTO;
import com.mdd.admin.dto.system.user.SystemUserSelvesDTO;
import com.mdd.admin.service.system.user.IUserService;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.validate.system.SystemUserCreateValidate;
import com.mdd.admin.validate.system.SystemUserSearchValidate;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.ToolsUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liushan
 * @data 2023/4/1 0:33
 */
public class UserTest extends BaseTest {

    @Autowired
    private IUserService userService;
    @Test
    public void pageUserTest() {
        PageValidate pageValidate = new PageValidate();
        pageValidate.setPageNo(1);
        pageValidate.setPageSize(5);
        SystemUserSearchValidate searchValidate = new SystemUserSearchValidate();
        searchValidate.setUsername("admin");
        PageResult<SystemUserBasicListDTO> list = userService.list(pageValidate, searchValidate);
        System.out.println(list);

    }

    @Test
    public void getSelvesTest() {
        SystemUserSelvesDTO self = userService.self(1L);
        System.out.println("*********" + self.getUser().toString());

    }

    @Test
    public void addUser() {
        SystemUserCreateValidate createValidate = new SystemUserCreateValidate();
        createValidate.setUsername("lisa");
        createValidate.setAvatar("http://127.0.0.1:8082/api/uploads/image/20230404/af26b44f-4c6f-4749-ab4d-2a3aebab269a.jpg");
        createValidate.setIsDisable(0);
        createValidate.setPassword("111111");
        createValidate.setNickname("丽萨");
        createValidate.setGender(2);
        createValidate.setSort(1);
        createValidate.setRoleIdList(Lists.newArrayList());
        createValidate.setDeptIdList(Lists.newArrayList(1L,2L));
        createValidate.setPostIdList(Lists.newArrayList());
        createValidate.setIsMultipoint(0);
        userService.add(createValidate);
    }

    @Test
    public void updateUser() {
//        SystemUserUpdateValidate validate = new SystemUserUpdateValidate();
//        validate.setPassword("liushan");
//        userService.edit(validate, 3L);
        String salt = ToolsUtils.randomString(5);
        System.out.println(salt);
        String s = ToolsUtils.makeMd5("123456" + salt);
        System.out.println(s);

    }
}
