package com.thread.manage;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 线程的休眠与恢复
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-24
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class FileClock implements Runnable {

	@Override
	public void run() {
		for(int i=0;i<10;i++){
			System.out.println(new Date());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				System.out.println("The FileClock has been interrupted");
			}
		}
	}
	
	public static void main(String[] args) {
		FileClock clock = new FileClock();
		Thread thread = new Thread(clock);
		thread.start(); //虽然主线程5秒时就中断，但执行完该线程需要10秒
		try {
			Thread.sleep(5000);
			//TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.interrupt();
	}
	
}
