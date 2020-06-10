package com.zht.exception;

/**
 * 错误编码
 *
 * @author ZHT
 */

public enum ErrorCode {

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(-1, "未知错误"),
    AUTH_FAILED(-10, "认证失败");


    private int value;
    private String desc;

    ErrorCode(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }

}
