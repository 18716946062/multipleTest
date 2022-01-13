package com.test.demo.aop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AopService {

    public void test(String s,Integer i) {
        log.info("======aop test method========");

    }

}
