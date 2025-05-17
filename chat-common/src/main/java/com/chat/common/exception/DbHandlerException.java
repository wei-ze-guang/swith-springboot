package com.chat.common.exception;


/**
 * @author 86199
 */
public class DbHandlerException extends RuntimeException {

    private final int code;

    public DbHandlerException(ErrorCodeEnum errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
    }

    public DbHandlerException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
