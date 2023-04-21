package com.mdd.common.enums;

import com.mdd.common.core.basics.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相册枚举
 */
@Getter
@AllArgsConstructor
public enum CourseCategoryEnum implements CodeEnum {

    SWIMMING(1, "游泳"),
    GROUP_CLASS(2, "团课");

    private final Integer code;
    private final String msg;

}
