package com.thread.synassist;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 等待多个并发事件的完成
 * 初始化要完成操作的数目，完成一个后调用countDown()方法，内部计数器减一
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-28
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		//表示要等待10个与会者到齐
		Videoconference conference = new Videoconference(10);
		Thread threadConference = new Thread(conference);
		threadConference.start();
		//10个与会者对象
		for (int i = 0; i < 10; i++) {
			Participant p = new Participant(conference, "Participant " + i);
			Thread t = new Thread(p);
			t.start();
		}
	}
}

//创建视频会议类
class Videoconference implements Runnable {
	//声明CountDownLatch对象
	private final CountDownLatch controller;
	public Videoconference(int number) {
		//定义必须等待先行完成的操作数目
		this.controller = new CountDownLatch(number);
	}
	//每个会议者进入视频会议时将被调用
	public void arrive(String name) {
		System.out.printf("%s has arrived.\n", name);
		//每个等待的事件完成时调用，计数器减一，当达到0时，CountDownLatch将唤起所有在await()等待的线程
		controller.countDown();
		//打印还没到达的与会者数目
		System.out.printf("VideoConference: waiting for %d participants.\n", controller.getCount());
	}
	
	@Override
	public void run() {
		//打印出这次视频会议的人数
		System.out.printf("VideoConference: Initialization: %d participants.\n", controller.getCount());
		try {
			controller.await();
			//所有人到齐后执行
			System.out.println("VideoConference: All the participants have come");
			System.out.println("VideoConference: Let's start...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//与会者
class Participant implements Runnable {
	private Videoconference conference;
	private String name;
	
	public Participant(Videoconference conference, String name) {
		this.conference = conference;
		this.name = name;
	}

	@Override
	public void run() {
		long duration = (long) (Math.random() * 5);
		try {
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//表示到来
		conference.arrive(name);
	}
	
}