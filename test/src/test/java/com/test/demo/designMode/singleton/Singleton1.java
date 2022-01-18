package com.test.demo.designMode.singleton;
/**
 * 饿汉式1
 * 静态变量创建类的对象
 */
public class Singleton1 {

    // 创建一个实例对象
    private static Singleton1 instance = new Singleton1();

    // 私有构造方法 防止被实例化
    private Singleton1() {}

    // 对外提供静态方法获取该对象
    public static Singleton1 getInstance() {
        return instance;
    }



}
