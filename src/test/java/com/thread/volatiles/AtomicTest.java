package com.thread.volatiles;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
	
	/**
	 * 原子类
	 */
	public static AtomicInteger race = new AtomicInteger(0);
	
	public static void increase() {
		/**
		 * incrementAndGet()方法具有原子性，属于非阻塞同步，相较于volatile阻塞同步效率更高
		 */
		race.incrementAndGet();
	}
	
	private static final int THREADS_COUNT = 20;
	
	public static void main(String[] args) {
		Thread[] threads = new Thread[THREADS_COUNT];
		for (int i = 0; i < THREADS_COUNT; i++) {
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						increase();
					}
				}
			});
			threads[i].start();
		}
		
		//等待所有累计线程都结束
		while (Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println(race);//执行结果：一个小于200000的数
	}

}
