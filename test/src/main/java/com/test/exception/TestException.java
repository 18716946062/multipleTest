package com.test.exception;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/17 10:42
 * @description: 自定义异常类格式
 */
public class TestException extends RuntimeException {

    private String code;

    public TestException(Object message) {
        super(message.toString());
    }

    public TestException(String code, Object message) {
        super(message.toString());
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
