package com.thread.volatiles;

/**
 * 变量自增运算测试
 * volatile关键字：
 * 	1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * 	2）禁止进行指令重排序。
 * 适用volatile的场合：
 *  1）运算结果不依赖变量的当前值，或者能确保只有单一的线程修改变量的值
 *  2）变量不需要与其他状态变量共同参与不变约束
 * @author fangxilin
 * @date 2018年1月29日
 */
public class VolatileTest {

	public static volatile int race = 0;
	
	public static void increase() {
		/**
		 * 自增操作是不具备原子性的，它包括读取变量的原始值、进行加1操作、写入工作内存，
		 * 
		 */
		race++;
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
