package com.test.controller;

import com.test.eunm.TestExceptionEnum;
import com.test.exception.TestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZSY
 * @program: test2
 * @date 2021/12/30 11:27
 * @description:
 */

@Slf4j
@RestController
public class TestController {

    @PostMapping("/test")
    public String test(){
        log.info(">>>>>>>>进入test");
        return "testtttttttttttttt";
    }

    @PostMapping("/testException")
    public String testException(){
        /** 0除异常 ArithmeticException */
        //int i = 1/0;
        /** 自定义异常 */
        if (true) {
            throw new TestException(TestExceptionEnum.MY_ERROR.getCode(), TestExceptionEnum.MY_ERROR.getMessage());
        }
        return "testExceptionnnnnnnnnn";
    }
}
