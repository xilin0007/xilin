package com.thread.synchronize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 锁（new ReentrantLock()）实现同步
 * 对lock()和unlock()间的代码上锁，实现同步
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class LockTest {
	public static void main(String[] args) {
		PrintQueue printQueue = new PrintQueue();
		//创建10个打印类
		int num = 5;
		Thread[] thread = new Thread[num];
		for (int i = 0; i < num; i++) {
			thread[i] = new Thread(new Job(printQueue), "Thread " + i);
		}
		for (int i = 0; i < num; i++) {
			thread[i].start();
			System.out.println(thread[i].getName() + ":" + thread[i].isAlive());
		}
	}
}

//打印队列类
class PrintQueue {
	//声明一个锁对象
	private final Lock queueLock = new ReentrantLock();
	
	//修改锁的公平性，默认是false不公平
	//private final Lock queueLock = new ReentrantLock(true);
	
	public void printJob(Object document) {
		//加锁并获取锁，当获取不到锁的时候，会一直等待。直到获取到锁。
		//queueLock.lock();
		//获取锁的时候，制作一次试探，如果获取锁失败，就不会一直等待的
		queueLock.tryLock();
		try {
			Long duration = (long) (Math.random() * 1000);
			System.out.println(Thread.currentThread().getName() + 
					": PrintQueue: Printing a Job during " + duration + " millisecond");
			//Thread.sleep(duration);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//释放锁对象，让其他线程访问，如果未释放，其他线程将永久等待，将导致死锁（Deadlock）
			queueLock.unlock();
		}
	}
}

//打印工作类
class Job implements Runnable {
	
	private PrintQueue printQueue;
	public Job(PrintQueue printQueue) {
		this.printQueue = printQueue;
	}

	@Override
	public void run() {
		System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
		printQueue.printJob(new Object());
		System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
	}
}