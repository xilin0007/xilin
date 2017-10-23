package com.thread.manage;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**join()方法*/
public class JoinTest {
	public static void main(String[] args) {
		DataSourcesLoader dsLoader = new DataSourcesLoader();
		Thread t1 = new Thread(dsLoader, "DataSourceThread");
		NetworkConnectionsLoader ncLoader = new NetworkConnectionsLoader();
		Thread t2 = new Thread(ncLoader, "NetworkConnectionLoader");
		t1.start();
		t2.start();		
		try {
			//t1.join(); //主线程调用t1线程，join使t1线程执行完再执行main线程
			//t1.join(3000); //限时3秒，如果3秒内t1未执行完，就不等t1执行完，继续执行main
			//t2.join();
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Main: Configuration has been loaded:"+new Date());
	}
}

class DataSourcesLoader implements Runnable {
	@Override
	public void run() {
		System.out.println("Beginning data sources loading:"+new Date());
		try {
			TimeUnit.SECONDS.sleep(4);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Data sources loading has finished:"+new Date());
	}
}

class NetworkConnectionsLoader implements Runnable {
	@Override
	public void run() {
		System.out.println("Beginning network Connections loading:"+new Date());
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Network Connections loading has finished:"+new Date());
	}
}