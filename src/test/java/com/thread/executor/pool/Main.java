package com.thread.executor.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		MyExecutor myExecutor = new MyExecutor(2, 4, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
		//存放即将传递给执行器的任务返回结果
		List<Future<String>> results = new ArrayList<>();
		//提交10个Task对象
		for (int i = 0; i < 10; i++) {
			SleepTwoSecondsTask task = new SleepTwoSecondsTask();
			Future<String> result = myExecutor.submit(task);
			results.add(result);
		}
		//输出前5个任务的执行结果
		for (int i = 0; i < 5; i++) {
			try {
				String result = results.get(i).get();
				System.out.println("Main: Result for Task" + i + ": " + result);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//完成executor的执行
		myExecutor.shutdown();
		
		
		//输出后5个任务的执行结果
		for (int i = 0; i < 5; i++) {
			try {
				String result = results.get(i).get();
				System.out.println("Main: Result for Task " + i + ": " + result);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//awaitTermination()等待执行器的完成
		try {
			myExecutor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Main: End of the program.");
	}
}
