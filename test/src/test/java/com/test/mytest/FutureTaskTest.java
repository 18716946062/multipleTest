package com.test.mytest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: ZSY
 * @program: multipleTest
 * @date 2022/1/21 16:15
 * @description: FutureTask测试
 */

@Slf4j
@SpringBootTest
public class FutureTaskTest {

    //模拟请求数据库得到数据    用map接收
    public static Map<String, String> requestDB(List<String> ipList) {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < ipList.size(); i++) {
            map.put(ipList.get(i), "返回的结果");
            // System.out.println(ipList.get(i)+" "+"12.03.65.2");
        }
        return map;
    }

    /**
     * 将list切割
     * @param list
     * @param n
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int n) {
        List<List<T>> res = new ArrayList<List<T>>();
        int remaider = list.size() % n; // (先计算出余数)
        int number = list.size() / n; // 然后是商
        int offset = 0;// 偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = list.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = list.subList(i * number + offset, (i + 1) * number + offset);
            }
            res.add(value);
        }
        return res;
    }

    public static void main(String[] args) {
        // 创建任务集合
        List<FutureTask<Map<String, String>>> taskList = new ArrayList<FutureTask<Map<String, String>>>();
        // 创建线程池
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1002; i++) { // 代替1000个ip
            list.add(i + "");
        }
        System.out.println(list.size());
        int listSize = 100; // 代替每次去请求远程接口的最大数
        int threadNumber = list.size() / listSize + (list.size() % listSize > 0 ? 1 : 0);
        List<List<String>> res = splitList(list, threadNumber);

        for (int i = 0; i < threadNumber; i++) {
            // 传入Callable对象创建FutureTask对象
            FutureTask<Map<String, String>> futureTask = new FutureTask<Map<String, String>>(
                    new ComputeTask(res.get(i)));
            taskList.add(futureTask);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            cacheThreadPool.submit(futureTask);

        }

        // 开始统计各计算线程计算结果
        Map<String, String> totalResult = new HashMap<>();
        for (FutureTask<Map<String, String>> ft : taskList) {
            try {
                // FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult.putAll(ft.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        cacheThreadPool.shutdown();
        System.out.println(totalResult.size());
        totalResult.forEach((k, v) -> {
            System.out.println("k: " + k + " v: " + v);
        });
    }

    private static class ComputeTask implements Callable<Map<String, String>> {
        private List<String> list;

        public ComputeTask(List<String> list) {
            this.list = list;
        }

        @Override
        public Map<String, String> call() throws Exception {
            return requestDB(list);
        }
    }


}
