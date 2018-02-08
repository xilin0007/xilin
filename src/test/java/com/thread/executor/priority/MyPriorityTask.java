package com.thread.executor.priority;

import java.util.concurrent.TimeUnit;

public class MyPriorityTask implements Runnable, Comparable<MyPriorityTask> {

	private int priority;
	
	private String name;
	
	public MyPriorityTask(int priority, String name) {
		this.priority = priority;
		this.name = name;
	}
	public int getPriority() {
		return priority;
	}

	@Override
	public void run() {
		System.out.println("MyPriorityTask: " + name + " Priority: " + priority);
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(MyPriorityTask o) {
		if (this.getPriority() < o.getPriority()) {
			return 1;
		}
		if (this.getPriority() > o.getPriority()) {
			return -1;
		}
		return 0;
	}

}
