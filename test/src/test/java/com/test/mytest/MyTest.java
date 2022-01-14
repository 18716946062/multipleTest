package com.test.mytest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.test.entity.User;
import com.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.FutureTask;

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
    private UserService userService;;

    @Test
    public void test(){
        long start = System.currentTimeMillis();
        //userService.list().forEach(System.out::println);
        userService.list();
        long end = System.currentTimeMillis();
        log.info("耗时： {}秒", (end - start)/1000);
    }

    @Test
    public void threadUserTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            List<User> list = userService.list();
        }
        long end = System.currentTimeMillis();
        log.info("耗时： {}秒", (end - start)/1000);

    }

    @Test
    public void threadUserTest2() {
        long start = System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            List<User> list = userService.list();
            return "success";
        });
        for (int i = 0; i < 10000; i++) {
            new Thread(futureTask).start();
        }
        long end = System.currentTimeMillis();
        log.info("耗时： {}秒", (end - start)/1000);
    }

}
