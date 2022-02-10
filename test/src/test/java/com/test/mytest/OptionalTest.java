package com.test.mytest;

import com.test.entity.User;
import com.test.exception.TestException;
import com.test.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/2/9 14:18
 * @description:
 */
@SpringBootTest
@Slf4j
public class OptionalTest {

    @Autowired
    private UserService userService;

    @Test
    public void orElse() {
        User u = userService.getById(10);
        User user = Optional.ofNullable(u).orElse(new User());
        System.out.println(user);
    }

    @Test
    public void orElseThrow() {
        User u = userService.getById(8);
        Optional.ofNullable(u).orElseThrow(() -> new TestException(">>>>抛出异常"));
    }

    @Test
    public void orElseGet() {
        User u = userService.getById(9);
        User user = Optional.ofNullable(u).orElseGet(() -> new User());
        System.out.println(user);
    }

    @Test
    public void filter() {
        User user = userService.getById(8);
        Optional<User> op = Optional.ofNullable(user).filter(u -> Objects.equals(u.getAddress(), "cq1"));
        System.out.println(op.get());
    }

    @Test
    public void map() {
        User user = userService.getById(7);
        Optional<String> op = Optional.ofNullable(user).map(u -> {
            return u.getName();
        });
        System.out.println(op.get());
    }

    @Test
    public void ifPresent() {
        User user = userService.getById(8);
        System.out.println(user);
        Optional.ofNullable(user).ifPresent(u -> {
            u.setName("名称");
            u.setAddress("地址");
            u.setId(777);
            u.setAge(20);
            u.setGender("男");
        });
        System.out.println(user);
    }




}
