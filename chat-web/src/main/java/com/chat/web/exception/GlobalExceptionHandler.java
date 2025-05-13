package com.chat.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @param ex 异常对象
     * @param model 模型对象
     * @return
     */
    // 添加@ExceptionHandler,表示该方法是处理异常的方法，属性为处理的异常类
    @ExceptionHandler({java.lang.NullPointerException.class,java.lang.ArithmeticException.class})
    public String exceptionHandler1(Exception ex, Model model){
        System.out.println("全局错误处理器工作");
        // 向模型中添加异常对象
        model.addAttribute("msg",ex);
        // 跳转到异常页面
        return "error";
    }
    // 方法一不能处理的异常交给方法二处理
    @ExceptionHandler({java.lang.Exception.class})
    public String exceptionHandler2(Exception ex,Model model){
        System.out.println("全局错误处理器工作");
        model.addAttribute("msg",ex);
        return "error2";
    }
}
