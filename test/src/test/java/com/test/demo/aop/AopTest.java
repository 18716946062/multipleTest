package com.test.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: ZSY
 * @program: test
 * @date 2022/1/6 14:12
 * @description:
 */
@SpringBootTest
@Slf4j
public class AopTest {
    @Autowired
    private AopService aopService;

    @Test
    public void aopTest(){
        aopService.test("s" , 1);
    }
}
