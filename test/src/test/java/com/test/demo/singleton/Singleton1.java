package com.test.demo.singleton;
/**
 * 饿汉式1
 * 静态变量创建类的对象
 */
public class Singleton1 {

    //私有构造方法
    private Singleton1() {}

    //在成员位置创建该类的对象
    private static Singleton1 instance = new Singleton1();

    //对外提供静态方法获取该对象
    public static Singleton1 getInstance() {
        return instance;
    }



}
