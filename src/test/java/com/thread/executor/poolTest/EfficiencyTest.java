package com.thread.executor.poolTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author fangxilin
 * @Description TODO
 * @date 2019-01-18
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2019
 */
public class EfficiencyTest {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        /**
         * 参数maximumPoolSize：
         * ArrayBlockingQueue<Runnable>为指定大小存放任务的队列,
         * 如果队列中任务已满，并且当前线程个数小于maximumPoolSize，那么会创建新的线程来执行任务。
         * LinkedBlockingQueue<Runnable>该队列无大小限制，所以maximumPoolSize参数一般不生效
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 3, 10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        List<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            Future<String> submit = pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //System.out.println("执行线程-" + ii);
                    System.out.println("线程名称："+Thread.currentThread().getName());
                    //休眠2s
                    TimeUnit.SECONDS.sleep(10);
                    return new Date().toString();
                }
            });
            results.add(submit);
        }
        //完成executor的执行
        pool.shutdown();
        long end = System.currentTimeMillis();
        //System.out.println(results.size());
        //System.out.println(end - start);
        /*for (int i = 0; i < results.size(); i++) {
            Future<String> ret = results.get(i);
            System.out.println("ret" + i + ": " + ret.get());
        }*/
    }
}
