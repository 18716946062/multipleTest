package com.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ZSY
 * @program: test2
 * @date 2021/12/30 11:27
 * @description:
 */

@Slf4j
@RestController
public class TestController {

    @PostMapping("test")
    public String test(@RequestParam String str){
        return str;
    }

    @PostMapping("/login")
    public String login(){
        log.info(">>>>>>>>登录进入");
        return "redirect:index.html";
    }
}
