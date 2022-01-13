package com.test.mytest;


import com.test.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class StreamTest {

    public List<User> getList() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "xiaomi", "cq", "男",16));
        list.add(new User(2, "huawei", "sz", "男",17));
        list.add(new User(3, "lisi", "gz", "女",18));
        list.add(new User(4, "wangwu", "sz", "男",19));
        list.add(new User(5, "xiangming", "bj", "女",20));
        list.add(new User(6, "zhang", "cq", "男",21));
        list.add(new User(7, "wenna", "cq", "女",22));
        list.add(new User(8, "xiaown", "bj", "男",23));
        return list;
    }

    @Test
    public void test() {
        List<User> list = getList();

        list.stream()
                .filter(f -> f.getAddress().equals("cq"))
                .filter(f -> f.getAge()==23)
                .forEach(System.out::println);
        System.out.println("=========================================================================================");
        List strList = new ArrayList<String>();
        list.stream().filter(m -> m.getAddress().equals("cq"))
                .map(m -> m.getName())
                .map(m -> {
                    return m.substring(0, 4);
                }).forEach(System.out::println);
        System.out.println("=========================================================================================");
        list.stream().peek(m -> {
            if (m.getAddress().equals("cq")) {
              m.setName("cqqqqqqq");
          }
        }).forEach(System.out::println);
        System.out.println("=========================================================================================");
        // peek 和 map
        list.stream().map(m -> {
            User user = new User();
            user.setId(m.getId() + 10);
            return user;
        }).forEach(System.out::println);
        list.stream().peek(p -> {
            p.setId(p.getId() + 10);
        }).forEach(System.out::println);
    }


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
