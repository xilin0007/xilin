package com.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行器中执行任务并返回结果（Callable）
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-28
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class CallableTest {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		List<Future<Integer>> resultList = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			Integer number = random.nextInt(10);
			FactorialCalculator calculator = new FactorialCalculator(number);
			//submit()方法发送任务给执行器
			Future<Integer> result = executor.submit(calculator);
			resultList.add(result);
		}
		do {
			System.out.printf("Main: Number of Completed Tasks: %d\n", executor.getCompletedTaskCount());
			for (int i = 0; i < resultList.size(); i++) {
				Future<Integer> result = resultList.get(i);
				//isDone(),任务是否已完成
				System.out.printf("Main: Task %d: %s\n", i, result.isDone());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (executor.getCompletedTaskCount() < resultList.size());
		
		//输出结果
		System.out.printf("Main: Results\n");
		Integer number = null;
		for (int i = 0; i < resultList.size(); i++) {
			Future<Integer> result = resultList.get(i);
			try {
				number = result.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("Main: Task %d: %d\n", i, number);
		}
		
		//结束
		executor.shutdown();
	}
}

//实现Callable
class FactorialCalculator implements Callable<Integer> {
	private Integer number;
	
	public FactorialCalculator(Integer number) {
		this.number = number;
	}

	//返回number的阶乘
	@Override
	public Integer call() throws Exception {
		int result = 1;
		if (number == 0 || number == 1) {
			result = 1;
		} else {
			for (int i = 2; i < number; i++) {
				result *= i;
				TimeUnit.MILLISECONDS.sleep(20);
			}
		}
		System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
		return result;
	}
	
}