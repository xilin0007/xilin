package com.thread.synchronize;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock读写锁实现获取和设置价格
 * 读锁：readLock(), 可允许多个线程同时访问
 * 写锁：writeLock(), 同时只能一个写线程访问，期间不能执行读操作
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ReadWriteLockTest {
	public static void main(String[] args) {
		PricesInfo pricesInfo = new PricesInfo();
		//创建5个读取类线程
		Reader[] readers = new Reader[5];
		Thread[] threadsReader = new Thread[5];
		for (int i = 0; i < 5; i++) {
			readers[i] = new Reader(pricesInfo);
			threadsReader[i] = new Thread(readers[i]);
		}
		//创建1个写类线程
		Writer writer = new Writer(pricesInfo);
		Thread threadWriter = new Thread(writer);
		
		//启动线程
		for (int i = 0; i < 5; i++) {
			threadsReader[i].start();
		}
		threadWriter.start();
	}
}

class PricesInfo {
	//初始化锁对象
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	private double price1;
	private double price2;
	public PricesInfo() {
		this.price1 = 1.0;
		this.price2 = 2.0;
	}
	
	//读锁获取对这个属性的访问
	public double getPrice1() {
		lock.readLock().lock();
		double value = price1;
		lock.readLock().unlock();
		return value;
	}
	//读锁获取对这个属性的访问
	public double getPrice2() {
		lock.readLock().lock();
		double value = price2;
		lock.readLock().unlock();
		return value;
	}
	
	//写锁控制对这两个属性的访问
	public void setPrices(double price1, double price2) {
		lock.writeLock().lock();
		this.price1 = price1;
		this.price2 = price2;
		lock.writeLock().unlock();
	}
	
}

class Reader implements Runnable {
	
	private PricesInfo pricesInfo;
	public Reader(PricesInfo pricesInfo) {
		this.pricesInfo = pricesInfo;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.printf("%s: Price 1: %f\n", Thread.currentThread().getName(), pricesInfo.getPrice1());
			System.out.printf("%s: Price 2: %f\n", Thread.currentThread().getName(), pricesInfo.getPrice2());
		}
		
	}
}

class Writer implements Runnable {
	
	private PricesInfo pricesInfo;
	public Writer(PricesInfo pricesInfo) {
		this.pricesInfo = pricesInfo;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.printf("Writer: Attempt to modify the prices.\n");
			pricesInfo.setPrices(Math.random() * 10, Math.random() * 8);
			System.out.printf("Writer: Prices have been modified.\n");
			try {
				//TimeUnit.SECONDS.sleep(1);
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}