package com.test.demo.proxy;


/**
 * @author zsy
 * @program : demo
 * date: 2021/8/27 13:59
 * Description: 自定义发送短息的接口 实现类
 */
public class SmsServiceImpl implements SmsService {

    @Override
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
