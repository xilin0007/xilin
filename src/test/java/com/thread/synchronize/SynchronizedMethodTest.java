package com.thread.synchronize;

/**
 * 同步锁方法实现存取钱
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class SynchronizedMethodTest {
	public static void main(String[] args) {
		Account account = new Account();
		account.setBalance(1000);
		Company company = new Company(account);
		Thread comThread = new Thread(company);
		Bank bank = new Bank(account);
		Thread bankThread = new Thread(bank);
		System.out.printf("Account: Initial Balance: %f\n", account.getBalance());
		comThread.start();
		bankThread.start();
		try {
			comThread.join();
			bankThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Account: Final Balance: %f\n", account.getBalance());
		/**
		 * 不加锁的话就会出现最后金额不一样的情况
		 * 当取钱时由于sleep，但这时却进行存钱操作，导致取完钱后还剩1000
		 * 
		 * 并且，由于多个线程几乎同一时间访问取钱，且都是基于1000的基础上，
		 * 所以就出现执行3次取钱，但最终却只是扣了一次的钱
		 */
	}
}

//银行账户类
class Account {
	private double balance;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	//存钱--加synchronized
	public synchronized void addAmount(double amount) {
		try {
			Thread.sleep(10); //设置存钱操作需要的时间延迟
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.balance += amount;
	}
	
	//取钱--加synchronized
	public void substractAmount(double amount) {
		synchronized (this) {
			try {
				Thread.sleep(10); //设置取钱操作需要的时间延迟
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.balance -= amount;
		}
	}
	
}

//ATM线程银行类，调用取钱
class Bank implements Runnable {
	
	private Account account;
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Bank(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			this.account.substractAmount(100);
			System.out.println("sub:"+this.account.getBalance());
		}
	}
}

//公司类，调用存钱
class Company implements Runnable {

	private Account account;
	
	public Company(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			this.account.addAmount(100);
			System.out.println("add:"+this.account.getBalance());
		}
	}
	
}
