package com.thread.executor.scheduled;

import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

	//任务开始时输出相应消息，然后休眠2秒
	@Override
	public void run() {
		System.out.println("Task: Begin.");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Task: End.");
	}
}
