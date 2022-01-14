package com.test.demo.thread.createThread;


import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: ZSY
 * @program: test
 * @date 2022/1/4 11:13
 * @description:
 */
@Slf4j
@SpringBootTest
public class CreateThread {

    @Test
    public void ttt() throws ExecutionException, InterruptedException {
        //extendThread();
        //implementRunnable();
        implementRunnable_result();
        //implementCallable();
    }

    // 1）继承Thread类创建线程
    public void extendThread() {
        new Thread("extendThread") {
            @Override
            public void run() {
                log.info(">>>>>>extendThread");
            }
        }.start();
    }

    // 2)实现Runnable接口
    public void implementRunnable() throws InterruptedException {
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info(">>>>>>implementRunnable");
            }
        };
        Thread.sleep(5000);
        new Thread(runnable, "implementRunnable").start();*/
        new Thread(() -> log.info(">>>>>>implementRunnable"), "implementRunnable")
                .start();
    }

    // 3)实现Runnable接口,带返回值
    public void implementRunnable_result() throws ExecutionException, InterruptedException {
        /*Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info(">>>>>>implementRunnable_result");
            }
        };
        FutureTask futureTask = new FutureTask(runnable, "implementRunnable_resulttttttttttt");*/
        FutureTask futureTask = new FutureTask(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(">>>>>>implementRunnable_result");
        },"implementRunnable_resulttttttttttt");
        new Thread(futureTask, "futureTask").start();
        log.info("implementRunnable_result(result): {}", futureTask.get());
    }

    // 4)带返回值的线程(实现implements  Callable<返回值类型>)
    public void implementCallable () throws ExecutionException, InterruptedException {
       /* FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                log.info(">>>>>>implementCallable");
                return "Callableeeeeeeeeeee";
            }
        });*/
        FutureTask<String> futureTask = new FutureTask(() -> {
            log.info(">>>>>>implementCallable");
            Thread.sleep(6000);
            return "Callableeeeeeeeeeee";
        });
        Thread thread = new Thread(futureTask, "implementCallable");
        thread.start();
        log.info("implementCallable(result): {}", futureTask.get());
    }


}
