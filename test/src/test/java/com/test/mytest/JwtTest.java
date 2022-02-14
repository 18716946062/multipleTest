package com.test.mytest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * @author: ZSY
 * @program: test
 * @date 2021/12/30 17:12
 * @description:
 */
@SpringBootTest
@Slf4j
public class JwtTest {

    /** jwtTest */
    @Test
    public void jwtTest() {
        String HMAC256 = "123456789";
        String token = getToken(HMAC256);
        log.info("token: {}", token);
        verifyToken(token, HMAC256);
    }

    public String getToken(String HMAC256) {
        HashMap<String, Object> map = new HashMap<>();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);

        String token = JWT.create()
                .withHeader(map) // header
                .withClaim("username", "zsy") // payload
                .withClaim("userid", 123)
                .withExpiresAt(instance.getTime()) // 过期时间
                .sign(Algorithm.HMAC256(HMAC256)); // 签名
        return token;
    }

    public void verifyToken(String token, String HMAC256){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(HMAC256)).build();
        DecodedJWT verify = jwtVerifier.verify(token);
        log.info("username: {}", verify.getClaim("username").asString());
        log.info("userid: {}", verify.getClaim("userid").asInt());
        log.info("Token: {}", verify.getToken());
        log.info("Expire: {}", verify.getExpiresAt());
    }
}
