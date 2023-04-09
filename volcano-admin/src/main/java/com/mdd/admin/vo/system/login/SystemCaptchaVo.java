package com.mdd.admin.vo.system.login;

import lombok.Data;

/**
 * 验证码
 */
@Data
public class SystemCaptchaVo {

    private String uuid;
    private String img;

}
