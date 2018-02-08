package com.thread.executor.scheduled;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//线程池中有两个线程
		MyScheduledThreadPoolExecutor executor = new MyScheduledThreadPoolExecutor(2);
		Task task = new Task();
		System.out.println("Main: " + new Date());
		//schedule()方法发送一个延迟任务到执行器，且延迟1秒后执行
		executor.schedule(task, 1, TimeUnit.SECONDS);
		
		TimeUnit.SECONDS.sleep(3);
		
		task = new Task();
		System.out.println("Main: " + new Date());
		//发送一个周期性任务到执行器，每3秒执行一次
		executor.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);
		
		TimeUnit.SECONDS.sleep(10);
		
		executor.shutdown();//关闭执行器
		executor.awaitTermination(1, TimeUnit.DAYS);//等待执行器结束
		
		System.out.println("Main: End of the program.");
	}

}
