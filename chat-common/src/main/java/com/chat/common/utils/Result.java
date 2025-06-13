package com.chat.common.utils;

public  class Result<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> OK(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.OK.getCode());
        result.setMsg(ResultCode.OK.getMsg());
        result.setData(data);
        return result;
    }
    public static <T> Result<T> FAIL(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAIL.getCode());
        result.setMsg(ResultCode.FAIL.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 错误请求
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> BAD_REQUEST(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.BAD_REQUEST.getCode());
        result.setMsg(ResultCode.BAD_REQUEST.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 网络错误
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> INTERNAL_ERROR(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.INTERNAL_ERROR.getCode());
        result.setMsg(ResultCode.INTERNAL_ERROR.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 修改失败
     * @param data
     * @return
     * @param <T>
     */

    public static <T> Result<T> MODIFICATION_FAILED(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.MODIFICATION_FAILED.getCode());
        result.setMsg(ResultCode.MODIFICATION_FAILED.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 删除失败
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> DELETION_FAILED(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.DELETION_FAILED.getCode());
        result.setMsg(ResultCode.DELETION_FAILED.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 创建失败
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Result<T> CREATION_FAILED(T data){
        Result<T> result = new Result<>();
        result.setCode(ResultCode.CREATION_FAILED.getCode());
        result.setMsg(ResultCode.CREATION_FAILED.getMsg());
        result.setData(data);
        return result;
    }

    /**
     * 未认证或未登录（401）
     */
    public static <T> Result<T> UNAUTHORIZED() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.UNAUTHORIZED.getCode());
        result.setMsg(ResultCode.UNAUTHORIZED.getMsg());
        return result;
    }

    /**
     * 已登录但权限不足（403）
     */
    public static <T> Result<T> FORBIDDEN() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FORBIDDEN.getCode());
        result.setMsg(ResultCode.FORBIDDEN.getMsg());
        return result;
    }

    /**
     * 验证码未通过（499）
     */
    public static <T> Result<T> captchaError() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.CAPTCHA_INVALID.getCode());
        result.setMsg(ResultCode.CAPTCHA_INVALID.getMsg());
        return result;
    }



    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
