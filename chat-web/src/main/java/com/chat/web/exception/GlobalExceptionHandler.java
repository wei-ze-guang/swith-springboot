package com.chat.web.exception;

import com.chat.common.exception.DbHandlerException;
import com.chat.common.exception.ErrorCodeEnum;
import com.chat.common.utils.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 资源未找到
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result handle404(NoHandlerFoundException e) {
        return Result.FAIL(ErrorCodeEnum.NOT_FOUND);
    }

    // 处理请求体参数校验失败异常（POST请求中@RequestBody校验）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return Result.FAIL("请求参数校验失败");
    }

    // 处理GET请求中@RequestParam、@PathVariable的参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.toList());
        return Result.FAIL("请求参数校验失败");
    }

    // 处理表单绑定异常
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleBindException(BindException ex) {
        List<String> errors = ex.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return Result.FAIL("请求参数绑定失败");
    }

    /**
     * 1. 处理唯一键冲突（Duplicate entry）
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        log.error("唯一约束冲突: {}", message, ex);
        if (message != null && message.contains("Duplicate entry")) {
            return Result.FAIL("记录已存在，不能重复添加");
        }
        return Result.FAIL("数据库约束错误: " + message);
    }

    /**
     * 2. 处理常规 SQL 错误（语法错误、字段不匹配等）
     */
    @ExceptionHandler(SQLException.class)
    public Result handleSQLException(SQLException ex) {
        log.error("SQL 异常: {}", ex.getMessage(), ex);
        return Result.FAIL(ErrorCodeEnum.FAIL);
    }

    /**
     * 3. Spring 封装的数据库访问异常（如连接超时、事务错误等）
     */
    @ExceptionHandler(DataAccessException.class)
    public Result handleDataAccessException(DataAccessException ex) {
        log.error("Spring DataAccess 异常: {}", ex.getMessage(), ex);
        return Result.FAIL(ErrorCodeEnum.FAIL);
    }



    /**
     * 自定义业务异常
     */
    @ExceptionHandler(DbHandlerException.class)
    public Result handleCustomException(DbHandlerException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.FAIL(e.getCode());
    }

    /**
     * 空指针等系统级错误
     */
    @ExceptionHandler(NullPointerException.class)
    public Result handleNullPointer(NullPointerException e) {
        log.error("空指针异常：", e);
        return Result.FAIL(ErrorCodeEnum.FAIL);
    }

    /**
     * 参数错误（如校验失败）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgument(IllegalArgumentException e) {
        log.warn("参数异常：{}", e.getMessage());
        return Result.FAIL(ErrorCodeEnum.PARAM_ERROR);
    }

    /**
     * 最后的异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler({java.lang.Exception.class})
    public Result exceptionHandler2(Exception ex){
        // 打印异常信息
        ex.printStackTrace(); // 控制台输出完整堆栈
        return Result.FAIL("全局处理器错误");
    }
}
