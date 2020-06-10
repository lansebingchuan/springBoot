package com.zht.dto;

import com.zht.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 异步返回数据
 * 异步返回数据
 *
 * @author ZHT
 */
@Data
@Builder
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static int STATUS_OK = 0;

    /**
     * 失败
     */
    public static int STATUS_ERROR = -1;

    /**
     * 返回状态
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    public static ApiResult error(String errormsg) {
        return builder().code(STATUS_ERROR).msg(errormsg).build();
    }

    public static ApiResult error(ErrorCode error) {
        return builder().code(error.getValue()).msg(error.getDesc()).build();
    }

    public static ApiResult error(Integer code, String errormsg) {
        return builder().code(code).msg(errormsg).build();
    }

    public static ApiResult ok(Object data) {
        return builder().code(STATUS_OK).data(data).build();
    }

    public static ApiResult ok() {
        return builder().code(STATUS_OK).build();
    }
}
