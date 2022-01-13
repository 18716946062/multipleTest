package com.test.demo.thread.synchronized_;

/**
 * @author zsy
 * @program : demo
 * date: 2021/8/31 8:51
 * Description: 线程
 */
public class Thread_synchronized {
    /**
     * synchronized 关键字解决的是多个线程之间访问资源的同步性，synchronized关键字可以保证被它修饰的方法或者代码块在任意时刻只能有一个线程执行。
     *
     * synchronized 关键字最主要的三种使用方式：
     *      1.修饰实例方法: 作用于当前对象实例加锁，进入同步代码前要获得 当前对象实例的锁
     *      synchronized void method() {
     *          //业务代码
     *      }
     *      2.修饰静态方法: 也就是给当前类加锁，会作用于类的所有对象实例 ，进入同步代码前要获得 当前 class 的锁。因为静态成员不属于任何一个实例对象，
     *      是类成员（ static 表明这是该类的一个静态资源，不管 new 了多少个对象，只有一份）。所以，如果一个线程 A 调用一个实例对象的非静态 synchronized 方法，
     *      而线程 B 需要调用这个实例对象所属类的静态 synchronized 方法，是允许的，不会发生互斥现象，因为访问静态 synchronized 方法占用的锁是当前类的锁，
     *      而访问非静态 synchronized 方法占用的锁是当前实例对象锁。
     *      synchronized static void method() {
     *          //业务代码
     *      }
     *      3.修饰代码块 ：指定加锁对象，对给定对象/类加锁。synchronized(this|object) 表示进入同步代码库前要获得给定对象的锁。synchronized(类.class)
     *      表示进入同步代码前要获得 当前 class 的锁
     *      synchronized(this) {
     *          //业务代码
     *      }
     *
     * 总结：
     *     synchronized 关键字加到 static 静态方法和 synchronized(class) 代码块上都是是给 Class 类上锁。
     *     synchronized 关键字加到实例方法上是给对象实例上锁。
     *     尽量不要使用 synchronized(String a) 因为 JVM 中，字符串常量池具有缓存功能！
     */


    /** 1.synchronized 同步语句块的情况 */
    public class SynchronizedDemo {
        public void method() {
            synchronized (this) {
                System.out.println("synchronized 代码块");
            }
        }
    }

    /** 2. synchronized 修饰方法的的情况 */
    public class SynchronizedDemo2 {
        public synchronized void method() {
            System.out.println("synchronized 方法");
        }
    }


}

