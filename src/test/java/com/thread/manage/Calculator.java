package com.thread.manage;

/**
 * 创建线程和线程的一些常用方法
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-24
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Calculator implements Runnable {
	
	private final int number;
	public Calculator(int number){
		this.number = number;
	}
	
	@Override
	public void run() {
		/*for(int i=1;i<=10;i++){
			System.out.printf("%s : %d * %d = %d\n", Thread.currentThread().getName(), number, i, i*number);
		}*/
		Thread t = Thread.currentThread();
		System.out.println("id: "+t.getId()+"-----"+"name: "+t.getName()+"-----"+"priority: "+t.getPriority()+"-----"+"state: "+t.getState());
	}
	
	public static void main(String[] args) {
		/*for(int i=1;i<=10;i++){
			Calculator ca = new Calculator(i);
			Thread thread = new Thread(ca);
			thread.start();
		}*/
		
		Thread[] threads = new Thread[10];
		Thread.State states[] = new Thread.State[10]; //线程运行状态
		for(int i=0;i<10;i++){
			threads[i] = new Thread(new Calculator(i));
			if(i%2 == 0){
				threads[i].setPriority(Thread.MAX_PRIORITY);
			}else{
				threads[i].setPriority(Thread.MIN_PRIORITY);
			}
			threads[i].setName("Thread "+i);
			threads[i].start();
		}
		
	}

}
