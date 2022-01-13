package com.test.mytest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;

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
        userService.list().forEach(System.out::println);
    }


}
