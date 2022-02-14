package com.test.mytest.jdk8;


import com.test.entity.User;
import com.test.exception.TestException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class StreamTest {

    public static List<User> getList() {
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
    public void filter() {
        List<User> list = getList();
        List<User> userList = list.stream().filter(f -> f.getAddress().equals("cq"))
                //.filter(f -> f.getAge().equals(22))
                .collect(Collectors.toList());
        userList.forEach(System.out::println);
    }

    @Test
    public void peek() {
        List<User> list = getList();
        List<User> userList = list.stream().peek(p -> {
            if (p.getAddress().equals("cq")) {
                p.setName("cqqqqqqq");
            }
        }).collect(Collectors.toList());
        userList.forEach(System.out::println);
    }

    @Test
    public void map() {
        List<User> list = getList();
        List<String> userList = list.stream().filter(m -> m.getAddress().equals("cq"))
                .map(m -> m.getName())
                .map(m -> m.substring(0, 4))
                .collect(Collectors.toList());
        userList.forEach(System.out::println);
    }









}
