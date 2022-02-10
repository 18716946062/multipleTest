package com.test.mytest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/2/10 10:36
 * @description:
 */

@SpringBootTest
@Slf4j
public class StringJointTest {

    @Test
    public void add() {
        StringJoiner where = new StringJoiner(" and ", "", "");
        Map<String, Object> map =new HashMap<>();
        map.put("signMethod", "111111");
        map.put("appSecret", "222222");
        map.put("addddd", 123321);

        String sql = "SELECT COUNT(*) FROM `%s` WHERE %s";
        map.forEach((k, v) -> where.add(k + "='" + v + "'"));
        log.info("where:  [{}]", where);
        System.out.println("where:  " + where);

        String str = String.format(sql, "tableName", where);
        System.out.println("str:  " + str);

        String s = "111;222;333;444;555;666";
        String[] split = s.split(";", 3);
        for (String st: split) {
            System.out.println(st);
        }
    }
}
