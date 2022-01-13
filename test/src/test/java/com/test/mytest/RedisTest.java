package com.test.mytest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: ZSY
 * @program: test
 * @date 2022/1/4 9:50
 * @description:
 */
@Slf4j
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void StringTest() {
        String myName= (String) redisTemplate.opsForValue().get("myName");
        String myName2=stringRedisTemplate.opsForValue().get("myName");
        String yourName2=stringRedisTemplate.opsForValue().get("yourName");
        log.info(">>>>>>>>>>myName:  {}", myName);
        log.info(">>>>>>>>>>myName2:  {}", myName2);
        log.info(">>>>>>>>>>yourName2:  {}", yourName2);
    }

    @Test
    public void ttt() {
        Boolean myName = redisTemplate.hasKey("myName");
        log.info("{}", myName);
    }

}
