package com.thread.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @Description TODO
 * @author fangxilin
 * @date 2020-07-08
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2020
 */
public class InterruptTest {
    public static void main(String args[]) throws Exception {
        Thread t1 = new Thread(() -> {

            try {
                //todo ...
                Thread.sleep(5000);
                //todo ...
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("1抛出异常后线程中断状态 -> " + Thread.currentThread().isInterrupted());
                //中断状态在抛出异常前，被清除掉，因此在此处重置中断状态
                Thread.currentThread().interrupt();
                System.out.println("1抛出异常且中断后线程中断状态 -> " + Thread.currentThread().isInterrupted());

            }
            System.out.println("线程中断后继续执行...");
            try {
                System.out.println("2线程初始中断状态 -> " + Thread.currentThread().isInterrupted());
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("2抛出异常后线程中断状态 -> " + Thread.currentThread().isInterrupted());
                e.printStackTrace();
            }
        });
        t1.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        //执行interrupt只是线程中抛出异常(sleep、wait等方法中才会)，具体需不需要终止线程，需要在线程run中执行Thread.currentThread().interrupt();
        /**
         * Thread.sleep()、Object.wait()、BlockingQueue.put()、BlockingQueue.take()等等。当线程执行正在这些方法时，
         * 被其他线程中断掉，该线程会首先清除掉中断状态（设置中断属性为false），然后抛出InterruptedException异常
         */
        t1.interrupt();
    }


}
