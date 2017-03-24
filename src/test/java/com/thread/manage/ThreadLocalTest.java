package com.thread.manage;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 线程局部变量
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-24
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ThreadLocalTest {
	public static void main(String[] args) {
		SafeTask task = new SafeTask();
		for(int i=0; i<3; i++) {
			Thread thread = new Thread(task);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}


class SafeTask implements Runnable {
	
	//定义线程局部变量
	private static ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
		@Override
		protected Date initialValue() {
			return new Date();
		}
	};

	@Override
	public void run() {
		System.out.printf("Starting Thread: %s, %s\n", Thread.currentThread().getId(), startDate.get());
		try {
			TimeUnit.SECONDS.sleep((int)Math.random()*10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Thread Finished: %s, %s\n", Thread.currentThread().getId(), startDate.get());
	}
}