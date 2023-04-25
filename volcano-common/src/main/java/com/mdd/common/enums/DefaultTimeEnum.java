package com.mdd.common.enums;

/**
 * 预约状态枚举
 */
public enum DefaultTimeEnum {

    NINE(9, "09:00"),
    TEN(10, "10:00"),
    ELEVEN(11, "11:00"),
    TWELVE(12, "12:00"),
    THIRTEEN(13, "13:00"),
    FOURTEEN(14, "14:00"),
    FIFTEEN(15, "15:00"),
    SIXTEEN(16, "16:00"),
    SEVENTEEN(17, "17:00"),
    EIGHTEEN(18, "18:00"),
    NINETEEN(19, "19:00"),
    TWENTY(20, "20:00"),
    TWENTY_ONE(21, "21:00"),
    TWENTY_TWO(22, "22:00");

    /**
     * 构造方法
     */
    private final int code;
    private final String msg;
    DefaultTimeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取状态码
     *
     * @author fzr
     * @return Long
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取提示
     *
     * @author fzr
     * @return String
     */
    public String getMsg() {
        return this.msg;
    }
    public static DefaultTimeEnum valueOfCode(int code) {
        for (DefaultTimeEnum value : DefaultTimeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

}
