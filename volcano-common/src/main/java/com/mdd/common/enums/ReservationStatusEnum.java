package com.mdd.common.enums;

/**
 * 预约状态枚举
 */
public enum ReservationStatusEnum {

    NOT_START(0, "未开始"),
    CANCEL(1, "已取消"),
    FINISH(2, "已完成");

    /**
     * 构造方法
     */
    private final int code;
    private final String msg;
    ReservationStatusEnum(int code, String msg) {
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
    public static ReservationStatusEnum valueOfCode(int code) {
        for (ReservationStatusEnum value : ReservationStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

}
