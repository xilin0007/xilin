package com.thread.executor.poolTest;

import java.util.Date;
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
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        for (int i = 0; i < 10; i++) {
            Future<String> submit = pool.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    //休眠2s
                    TimeUnit.SECONDS.sleep(2);
                    return new Date().toString();
                }
            });
        }
        //完成executor的执行
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println(start - end);
    }
}
