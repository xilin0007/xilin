package com.thread.synchronize;

/**
 * 同步代码块实现两个电影院两个售票处售票
 * 同步代码块中使用对象作为入参，
 * 保证同一时间只有一个线程能访问这个对象的所有同步代码块
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class SynchronizedCodeTest {
	public static void main(String[] args) {
		Cinema cinema = new Cinema();
		TicketOffice1 ticketOffice1 = new TicketOffice1(cinema);
		Thread thread1 = new Thread(ticketOffice1, "TicketOffice1");
		TicketOffice2 ticketOffice2 = new TicketOffice2(cinema);
		Thread thread2 = new Thread(ticketOffice2, "TicketOffice2");
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Room 1 Vacancies: %d\n", cinema.getVacanciesCinema1());;
		System.out.printf("Room 2 Vacancies: %d\n", cinema.getVacanciesCinema2());;
	}
}

//电影院类
class Cinema {
	private long vacanciesCinema1;//电影院1剩余的电影票数
	private long vacanciesCinema2;//电影院2剩余的电影票数
	/**
	 * 电影院1,2
	 * controlCinema1控制对vacanciesCinema1的同步访问，
	 * controlCinema2控制对vacanciesCinema2的同步访问
	 */
	private final Object controlCinema1, controlCinema2;
	
	public Cinema() {
		this.vacanciesCinema1 = 20;
		this.vacanciesCinema2 = 20;
		this.controlCinema1 = new Object();
		this.controlCinema2 = new Object();
	}
	
	//电影院1卖票时调用，controlCinema1控制synchronized代码块
	public boolean sellTickets1(int number) {
		synchronized (controlCinema1) {
			if(number < vacanciesCinema1){
				vacanciesCinema1 -= number;
				return true;
			}
			return false;
		}
	}
	//电影院1退票
	public boolean returnTickets1(int number) {
		synchronized (controlCinema1) {
			vacanciesCinema1 += number;
			return true;
		}
	}
		
	//电影院2卖票时调用，controlCinema2控制synchronized代码块
	public boolean sellTickets2(int number) {
		synchronized (controlCinema2) {
			if(number < vacanciesCinema2){
				vacanciesCinema2 -= number;
				return true;
			}
			return false;
		}
	}
	//电影院2退票
	public boolean returnTickets2(int number) {
		synchronized (controlCinema2) {
			vacanciesCinema2 += number;
			return true;
		}
	}

	public long getVacanciesCinema1() {
		return vacanciesCinema1;
	}
	public long getVacanciesCinema2() {
		return vacanciesCinema2;
	}
}

//售票处1
class TicketOffice1 implements Runnable {
	
	private Cinema cinema;
	public TicketOffice1(Cinema cinema) {
		this.cinema = cinema;
	}
	
	@Override
	public void run() {
		//售票处1售退两个电影院的票
		cinema.sellTickets1(3);
		cinema.sellTickets1(2);
		cinema.sellTickets2(2);
		cinema.returnTickets1(3);
		cinema.sellTickets1(5);
		cinema.sellTickets2(2);
		cinema.sellTickets2(2);
		cinema.sellTickets2(2);
	}
}
//售票处2
class TicketOffice2 implements Runnable {
	
	private Cinema cinema;
	public TicketOffice2(Cinema cinema) {
		this.cinema = cinema;
	}

	@Override
	public void run() {
		//售票处1售退两个电影院的票
		cinema.sellTickets2(2);
		cinema.sellTickets2(4);
		cinema.sellTickets1(2);
		cinema.sellTickets1(1);
		cinema.returnTickets2(2);
		cinema.sellTickets1(3);
		cinema.sellTickets2(2);
		cinema.sellTickets1(2);
	}
}

