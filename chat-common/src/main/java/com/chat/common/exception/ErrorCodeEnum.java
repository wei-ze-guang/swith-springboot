package com.chat.common.exception;

/**
 * @author 86199
 */

public enum ErrorCodeEnum {

    SUCCESS(200, "操作成功"),
    FAIL(500, "系统异常，请联系管理员"),
    DB_ERROR(501, "数据库访问异常"),
    PARAM_ERROR(400, "参数校验失败"),
    NOT_FOUND(404, "资源未找到");

    private final int code;
    private final String message;


    ErrorCodeEnum(int i, String  message) {
        this.code = i;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
