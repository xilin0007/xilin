package com.thread.manage;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 线程组对象
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-24
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ThreadGroupTest {
	
	public static void main(String[] args) {
		//创建一个标识为Searcher的线程组对象
		ThreadGroup threadGroup = new ThreadGroup("Searcher");
		SearchTask searchTask = new SearchTask("");
		for(int i=0; i<5; i++) {
			Thread thread = new Thread(threadGroup, searchTask);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//activeCount()方法获取线程组包含的线程数
		System.out.printf("Number of Threads: %d\n", threadGroup.activeCount());
		System.out.printf("Information about the Thread Group\n");
		//list()方法打印线程组对象信息
		threadGroup.list();
		
		Thread[] threads = new Thread[threadGroup.activeCount()];
		//enumerate(threads)方法将线程组中的对象赋值给一个线程数组
		threadGroup.enumerate(threads);
		for(int i=0; i<threadGroup.activeCount(); i++) {
			System.out.printf("Thread %s: %s\n", threads[i].getName(), threads[i].getState());
		}
		
		waitFinish(threadGroup);
		
		//interrupt()线程组中断，所有线程都中断
		threadGroup.interrupt();
		
	}
	
	private static void waitFinish(ThreadGroup threadGroup) {
		while(threadGroup.activeCount()>9) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class SearchTask implements Runnable {
	
	private String result;
	
	public SearchTask(String result) {
		this.result = result;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.printf("Thread %s: start\n", name);
		try {
			doTask();
			result = name;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.printf("Thread %s: Interrupted\n", name);
		}
	}
	
	private void doTask() throws InterruptedException {
		Random random = new Random(new Date().getTime());
		int value = (int) (random.nextDouble()*100);
		System.out.printf("Thread %s: %d\n", Thread.currentThread().getName(), value);
		TimeUnit.SECONDS.sleep(value);
	}
}