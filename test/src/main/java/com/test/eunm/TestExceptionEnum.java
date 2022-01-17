package com.test.eunm;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/17 10:47
 * @description: 异常枚举类
 */
public enum TestExceptionEnum {

    PARAMETER_ERROR("40001", "系统异常，入参错误"),

    MY_ERROR("40002", "系统异常，自定义异常");

    private String code;
    private String message;

    private TestExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TestExceptionEnum getEunm(String code) {
        TestExceptionEnum[] values = TestExceptionEnum.values();
        for (TestExceptionEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }


}
