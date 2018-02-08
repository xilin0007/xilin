package com.thread.executor.priority;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		/**
		 * 最大线程数为2，一次执行两个线程
		 * 传入priorityBlockingQueue参数
		 */
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
		//执行4个任务
		for (int i = 0; i < 4; i++) {
			MyPriorityTask task = new MyPriorityTask(i, "Task" + i);
			executor.execute(task);
		}
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//再执行4个任务
		for (int i = 4; i < 8; i++) {
			MyPriorityTask task = new MyPriorityTask(i, "Task" + i);
			executor.execute(task);
		}
		
		//关闭执行器
		executor.shutdown();

		//awaitTermination()等待执行器的完成
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Main: End of the program.");
	}
}
