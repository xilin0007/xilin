package com.thread.manage;

/**
 * 创建线程和线程中断
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-24
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class PrimeGenerator extends Thread {
	//判断是否是质数
	private boolean isPrime(long number){
		if(number <= 2){
			return true;
		}
		for(long i=2;i<number;i++){
			if(number%i == 0){
				return false;
			}
		}
		return true;
	}
	@Override
	public void run(){
		long num = 1L;
		while(true){
			if(isPrime(num)){
				System.out.println("Number "+num+" is Prime");
			}
			if(isInterrupted()){ //判断线程是否中断
				System.out.println("The Prime Generator has been Interrupted");
				return;
			}
			num++;
		}
	}
	
	public static void main(String[] args) {
		Thread task = new PrimeGenerator();
		task.start();
		try {
			System.out.println("-------主线程休眠开始，暂时暂停后面语句的执行");
			Thread.sleep(500);
			System.out.println("-------主线程休眠结束");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.interrupt(); //半秒后线程中断
	}

}
