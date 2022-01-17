package com.test.exception;

import com.test.eunm.TestExceptionEnum;
import com.test.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/17 10:26
 * @description: 全局异常
 */
@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    /** 除数不能为0 */
    @ExceptionHandler({ArithmeticException.class})
    public Result arithmeticException(ArithmeticException ex) {
        log.error("除数不能为0：{} ", ex.getMessage(), ex);
        return Result.failure("ERROR", "4000", "除数不能为零", "", "");
    }

    /** 自定义异常 */
    @ExceptionHandler({TestException.class})
    public Result arithmeticException(TestException tex) {
        log.error("自定义异常：{} ", tex.getMessage(), tex.getCode(), tex);
        return Result.failure("ERROR", tex.getCode(), tex.getMessage(), "", "");
    }

}
