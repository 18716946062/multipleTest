package com.test.mytest;

import com.test.entity.User;
import com.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: ZSY
 * @program: test2
 * @date 2021/12/30 13:39
 * @description:
 */
@SpringBootTest
@Slf4j
public class MyTest {
    @Autowired
    private UserService userService;

    @Test
    public void test(){
        userService.list().forEach(System.out::println);
    }

    public static void main(String[] args) {

    }

}
