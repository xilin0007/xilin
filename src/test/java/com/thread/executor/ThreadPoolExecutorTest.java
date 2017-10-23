package com.thread.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程执行器
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-28
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ThreadPoolExecutorTest {
	public static void main(String[] args) {
		Server server = new Server();
		for (int i = 0; i < 10; i++) {
			Task task = new Task("Task-" + i);
			server.executeTask(task);
		}
		server.endServer();
	}
}

class Task implements Runnable {
	
	private Date initDate;
	private String name;
	public Task(String name) {
		this.initDate = new Date();
		this.name = name;
	}

	@Override
	public void run() {
		System.out.printf("%s: Task %s: Created on: %s\n", Thread.currentThread().getName(), name, initDate);
		System.out.printf("%s: Task %s: Started on: %s\n", Thread.currentThread().getName(), name, new Date());
		try {
			Long duration = (long) (Math.random() * 10);
			System.out.printf("%s: Task %s: Doing a task during %d seconds\n", Thread.currentThread().getName(), name, duration);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("%s: Task %s: Finished on: %s\n", Thread.currentThread().getName(), name, new Date());
	}
	
}

//执行通过执行器收到的每一个任务
class Server {
	private ThreadPoolExecutor executor;

	public Server() {
		//通过Executors类初始化ThreadPoolExecutor线程池执行器
		//this.executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		//创建固定大小的线程执行器
		this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
	}
	
	//将任务对象发送给执行器
	public void executeTask(Task task) {
		System.out.println("Server: A new task has arrived\n");
		//调用执行器的execute()方法将任务发送给Task
		executor.execute(task);
		System.out.printf("Server: Pool Size: %d\n", executor.getPoolSize());
		System.out.printf("Server: Active Count: %d\n", executor.getActiveCount());
		System.out.printf("Server: Completed Tasks: %d\n", executor.getCompletedTaskCount());
		//获取已发送到执行器上的任务数
		System.out.printf("Server: Task Count: %d\n", executor.getTaskCount());
	}
	
	//结束线程
	public void endServer() {
		executor.shutdown();
	}
}