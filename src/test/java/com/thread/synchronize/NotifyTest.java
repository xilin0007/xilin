package com.thread.synchronize;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * wait(), notify(), notifyAll()
 * 线程调用set()如果满了，调用wait()方法挂起线程进入排队等待状态，
 * 当其他线程调用notifyAll()时，挂起的线程将被唤醒并且再次检查是否满足了条件
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class NotifyTest {
	public static void main(String[] args) {
		EventStorage eventStorage = new EventStorage();
		Producer producer = new Producer(eventStorage);
		Thread thread1 = new Thread(producer);
		Consumer consumer = new Consumer(eventStorage);
		Thread thread2 = new Thread(consumer);
		thread1.start();
		thread2.start();
	}
}

//数据存储类
class EventStorage {
	private int maxSize;
	private List<Date> storage;
	public EventStorage() {
		this.maxSize = 10;
		this.storage = new LinkedList<Date>();
	}
	
	//保存数据到storage中，如果已满，wait()方法挂起等待，之后再notify唤醒
	public synchronized void set() {
		while (storage.size() ==  maxSize) {
			try {
				//休眠排队等待状态，一般只能在同步代码中调用
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		storage.add(new Date());
		System.out.printf("Set: %d\n",storage.size());
		//唤醒所有线程
		notifyAll();
	}
	
	//从storage中获取数据
	public synchronized void get() {
		while (storage.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//poll移除掉第一个元素
		System.out.printf("Get: %d: %s\n", storage.size(), ((LinkedList<?>)storage).poll());
		notifyAll();
	}
}

//生产者类
class Producer implements Runnable {
	
	private EventStorage eventStorage;
	public Producer(EventStorage eventStorage) {
		this.eventStorage = eventStorage;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			//存入数据
			eventStorage.set();
		}
	}
}

//消费者类
class Consumer implements Runnable {

	private EventStorage eventStorage;
	public Consumer(EventStorage eventStorage) {
		this.eventStorage = eventStorage;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			//取出数据
			eventStorage.get();
		}
	}
	
}