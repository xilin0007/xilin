package com.thread.lock;

import java.util.List;




public class LockTest {
	public static void main(String[] args) {
		String property = System.getProperty("java.io.tmpdir");
		System.out.println(property);
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
	
	//存钱
	public synchronized void addAmount(double amount) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.balance += amount;
	}
	
	//取钱
	public synchronized void substractAmount(double amount) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.balance -= amount;
	}
	
}

//ATM线程银行类，调用取钱
class Bank implements Runnable {
	
	private final Account account;
	
	public Bank(Account account) {
		this.account = account;
	}

	@Override
	public void run() {
		try
	}
	
}





















