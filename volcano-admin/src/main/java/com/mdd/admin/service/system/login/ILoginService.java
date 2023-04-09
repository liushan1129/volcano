package com.mdd.admin.service.system.login;

import com.mdd.admin.validate.system.SystemAdminLoginsValidate;
import com.mdd.admin.vo.system.login.SystemCaptchaVo;
import com.mdd.admin.vo.system.login.SystemLoginVo;

/**
 * 系统登录服务接口类
 */
public interface ILoginService {

    /**
     * 验证码
     *
     * @author fzr
     * @return SystemCaptchaVo
     */
    SystemCaptchaVo captcha();

    /**
     * 登录
     *
     * @author fzr
     * @param loginsValidate 登录参数
     * @return SystemLoginVo
     */
    SystemLoginVo login(SystemAdminLoginsValidate loginsValidate);

    /**
     * 退出
     *
     * @author fzr
     * @param token 令牌
     */
    void logout(String token);

}
