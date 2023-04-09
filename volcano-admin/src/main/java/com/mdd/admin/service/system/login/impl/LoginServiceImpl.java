package com.mdd.admin.service.system.login.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.mdd.admin.config.AdminConfig;
import com.mdd.admin.service.system.user.IUserService;
import com.mdd.admin.service.system.login.ILoginService;
import com.mdd.admin.repo.SystemUserRepo;
import com.mdd.admin.validate.system.SystemAdminLoginsValidate;
import com.mdd.admin.vo.system.login.SystemCaptchaVo;
import com.mdd.admin.vo.system.login.SystemLoginVo;
import com.mdd.common.entity.system.SystemLogLogin;
import com.mdd.common.entity.user.UserBasic;
import com.mdd.common.enums.HttpEnum;
import com.mdd.common.exception.LoginException;
import com.mdd.common.exception.OperateException;
import com.mdd.common.mapper.system.SystemLogLoginMapper;
import com.mdd.common.mapper.system.SystemUserBasicMapper;
import com.mdd.common.util.*;
import com.google.code.kaptcha.Producer;
import nl.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * 系统登录服务实现类
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    Producer captchaProducer;

    @Resource
    SystemLogLoginMapper systemLogLoginMapper;

    @Resource
    SystemUserRepo userRepo;

    @Resource
    IUserService iUserService;

    @Resource
    private SystemUserBasicMapper userBasicMapper;


    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    /**
     * 验证码
     *
     * @author fzr
     * @return SystemCaptchaVo
     */
    @Override
    public SystemCaptchaVo captcha() {
        // 验证码信息
        String capStr, code;
        BufferedImage image;
        String uuid = ToolsUtils.makeUUID();
        String ip   = IpUtils.getIpAddress().replaceAll("\\.", "");
        String verifyKey = YmlUtils.get("like.captcha.token") + ip + ":" + uuid;
        long expireTime = Long.parseLong(YmlUtils.get("like.captcha.expire"));

        // 生成验证码
        capStr = code = captchaProducer.createText();
        image = captchaProducer.createImage(capStr);
        RedisUtils.set(verifyKey, code.toLowerCase(), expireTime);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            log.error("verifyCode Error:" + e.getMessage());
            throw new OperateException(e.getMessage());
        }

        // 返回验证码
        String base64 = "data:image/jpeg;base64,"+ Base64Util.encode(os.toByteArray());
        SystemCaptchaVo vo =  new SystemCaptchaVo();
        vo.setUuid(uuid);
        vo.setImg(base64);
        return vo;
    }

    /**
     * 登录
     *
     * @author fzr
     * @param loginsValidate 登录参数
     * @return SystemLoginVo
     */
    @Override
    public SystemLoginVo login(SystemAdminLoginsValidate loginsValidate) {
        String username = loginsValidate.getUsername();
        String password = loginsValidate.getPassword();

//        String captchaStatus = YmlUtils.get("like.captcha.status");
//        if (StringUtils.isNotNull(captchaStatus) && captchaStatus.equals("true")) {
//            Assert.notNull(loginsValidate.getCode(), "code参数缺失");
//            Assert.notNull(loginsValidate.getUuid(), "uuid参数缺失");
//            String ip   = IpUtils.getIpAddress().replaceAll("\\.", "");
//            String captchaKey = YmlUtils.get("like.captcha.token") + ip + ":" + loginsValidate.getUuid();
//            Object code = RedisUtils.get(captchaKey);
//            RedisUtils.del(captchaKey);
//            if (StringUtils.isNull(code) || StringUtils.isEmpty(code.toString()) || !loginsValidate.getCode().equals(code.toString())) {
//                throw new LoginException(HttpEnum.CAPTCHA_ERROR.getCode(), HttpEnum.CAPTCHA_ERROR.getMsg());
//            }
//        }

        long bf = System.currentTimeMillis();
        UserBasic userBasic = userRepo.queryFieldByCondition(null, username, null, null);
        System.out.println("登錄查詢用戶耗時：" + (System.currentTimeMillis() - bf));
        if (StringUtils.isNull(userBasic) || userBasic.getIsDelete().equals(1)) {
            this.recordLoginLog(0L, loginsValidate.getUsername(), HttpEnum.LOGIN_ACCOUNT_ERROR.getMsg());
            throw new LoginException(HttpEnum.LOGIN_ACCOUNT_ERROR.getCode(), HttpEnum.LOGIN_ACCOUNT_ERROR.getMsg());
        }

        if (userBasic.getIsDisable().equals(1)) {
            this.recordLoginLog(userBasic.getId(), loginsValidate.getUsername(), HttpEnum.LOGIN_DISABLE_ERROR.getMsg());
            throw new LoginException(HttpEnum.LOGIN_DISABLE_ERROR.getCode(), HttpEnum.LOGIN_DISABLE_ERROR.getMsg());
        }

        String newPWd = password + userBasic.getSalt();
        String md5Pwd = ToolsUtils.makeMd5(newPWd);
        if (!md5Pwd.equals(userBasic.getPassword())) {
            this.recordLoginLog(userBasic.getId(), loginsValidate.getUsername(), HttpEnum.LOGIN_ACCOUNT_ERROR.getMsg());
            throw new LoginException(HttpEnum.LOGIN_ACCOUNT_ERROR.getCode(), HttpEnum.LOGIN_ACCOUNT_ERROR.getMsg());
        }

        try {
            // 禁止多处登录
            if (userBasic.getIsMultipoint().equals(0)) {
                StpUtil.logout(userBasic.getId());
            }

            // 实现账号登录
            StpUtil.login(userBasic.getId());

            // 更新登录信息
            userBasic.setLastLoginIp(IpUtils.getIpAddress());
            userBasic.setLastLoginTime(System.currentTimeMillis() / 1000);
            userBasicMapper.updateById(userBasic);

            // 记录登录日志
            this.recordLoginLog(userBasic.getId(), loginsValidate.getUsername(), "");
            iUserService.cacheAdminUserByUid(userBasic.getId());

            // 响应登录信息
            SystemLoginVo vo = new SystemLoginVo();
            vo.setId(userBasic.getId());
            vo.setToken(StpUtil.getTokenValue());
            System.out.println("登錄耗時：" + (System.currentTimeMillis() - bf));
            return vo;
        } catch (Exception e) {
            Long adminId = StringUtils.isNotNull(userBasic.getId()) ? userBasic.getId() : 0;
            String error = StringUtils.isEmpty(e.getMessage()) ? "未知错误" : e.getMessage();
            this.recordLoginLog(adminId, loginsValidate.getUsername(), error);
            throw new OperateException(e.getMessage());
        }

    }

    /**
     * 退出
     *
     * @author fzr
     * @param token 令牌
     */
    @Override
    public void logout(String token) {
        RedisUtils.del(AdminConfig.backstageManageKey + token);
    }

    /**
     * 记录登录日志
     */
    private void recordLoginLog(Long adminId, String username, String error) {
        try {
            HttpServletRequest request = Objects.requireNonNull(RequestUtils.handler());
            final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

            SystemLogLogin model = new SystemLogLogin();
            model.setAdminId(adminId);
            model.setUsername(username);
            model.setIp(IpUtils.getIpAddress());
            model.setOs(userAgent.getOperatingSystem().getName());
            model.setBrowser(userAgent.getBrowser().getName());
            model.setStatus(StringUtils.isEmpty(error) ? 1 : 0);
            model.setCreateTime(System.currentTimeMillis() / 1000);
            systemLogLoginMapper.insert(model);
        } catch (Exception e) {
            log.error("记录登录日志异常 {}" + e.getMessage());
        }
    }

}
