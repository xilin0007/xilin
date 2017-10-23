package com.thread.manage.daemon;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

public class Task {
	public static void main(String[] args) {
		Deque<Event> deque = new ArrayDeque<Event>();
		//创建3个WriteTask线程和一个CleanerTask线程
		WriterTask w = new WriterTask(deque);
		for(int i=0;i<1;i++){
			Thread t = new Thread(w);
			t.start();
		}

		CleanerTask c = new CleanerTask(deque);//线程初始化的值赋值给了deque
		//c.setDaemon(true);
		c.start();
		System.out.println("=========>"+c.deque.size());
	}
	
}

class WriterTask implements Runnable {
	Deque<Event> deque; //双端队列
	
	public WriterTask(Deque<Event> deque) {
		this.deque = deque;
	}

	@Override
	public void run() {
		for(int i=1;i<30;i++){
			Event e = new Event();
			e.setDate(new Date());
			e.setEvent(String.format("The thread %s has generated an even"+i, Thread.currentThread().getId()));
			deque.addFirst(e);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

class CleanerTask extends Thread {
	Deque<Event> deque; //双端队列

	public CleanerTask(Deque<Event> deque) {
		this.deque = deque;
		setDaemon(true); //设置为守护线程
	}
	
	@Override
	public void run() {
		while(true){
			Date date = new Date();
			clean(date);
		}
	}
	
	private void clean(Date date){
		long difference;
		boolean delete;
		if(deque.size() == 0){
			return;
		}
		
		delete = false;
		do {
			Event e = deque.getLast();
			difference = date.getTime() - e.getDate().getTime();
			if(difference>10000){
				System.out.println("Cleaner:"+e.getEvent());
				deque.removeLast();
				delete = true;
			}
		} while (difference>10000);
		if(delete){
			System.out.println("Cleaner:Size of the queue:"+deque.size());
		}
	}
	
}