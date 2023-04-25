package com.mdd.common.enums;

/**
 * 特殊角色
 * @author liushan
 * @data 2023/4/3 10:06
 */
public enum SpecialRoleEnum {
    SUPER_ADMIN(0L, "超级管理员"),
    ;

    private Long code;
    private String name;

    SpecialRoleEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 获取状态码
     *
     * @author fzr
     * @return Long
     */
    public Long getCode() {
        return this.code;
    }

    /**
     * 获取提示
     *
     * @author fzr
     * @return String
     */
    public String getName() {
        return this.name;
    }

    public static SpecialRoleEnum valueOfCode(int code) {
        for (SpecialRoleEnum value : SpecialRoleEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
