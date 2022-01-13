package com.test.demo.singleton;

/**
 * 懒汉式
 * 线程不安全
 */
public class Singleton2 {
    //私有构造方法
    private Singleton2(){}

    //在成员位置创建该类的对象
    private static Singleton2 instance;

    //对外提供静态方法获取该对象
    public static Singleton2 getInstance() {
        if(instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }

}
