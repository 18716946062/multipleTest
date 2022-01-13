package com.test.demo.thread.threadpool;

import java.util.Date;

/**
 * @author: ZSY
 * @program: test
 * @date 2022/1/6 9:56
 * @description:
 */
public class MyRunnable implements Runnable{
    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}
