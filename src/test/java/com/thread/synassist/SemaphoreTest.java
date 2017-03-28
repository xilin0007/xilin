package com.thread.synassist;

import java.util.concurrent.Semaphore;

/**
 * Semaphore信号量
 * acquire()，获取信号量，然后信号量减一 	（lock）
 * release()，释放信号量，信号量加一	(unlock)
 * 只有信号量>0时表示资源可以访问
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-28
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class SemaphoreTest {
	public static void main(String[] args) {
		PrintQueue printQueue = new PrintQueue();
		Thread thread[] = new Thread[10];
		for (int i = 0; i < 10; i++) {
			thread[i] = new Thread(new Job(printQueue), "Thread" + i);
		}
		for (int i = 0; i < 10; i++) {
			thread[i].start();
		}
	}
}

class PrintQueue {
	//声明一个信号量对象
	private final Semaphore semaphore;

	public PrintQueue() {
		//初始化为1，相当于同一时间只能有一个线程访问
		this.semaphore = new Semaphore(1);
	}
	
	public void printJob(Object document) {
		//acquire方法获取信号量
		try {
			semaphore.acquire();
			long duration = (long) (Math.random() * 10);
			System.out.printf("%s: PrintQueue: Printing a Job during %d millisecond\n", Thread.currentThread().getName(), duration);
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//释放信号量
			semaphore.release();
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

