package com.thread.executor.pool;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 继承ThreadPoolExecutor类
 * @author fangxilin
 * @date 2018年2月6日
 */
public class MyExecutor extends ThreadPoolExecutor {
	
	
	private ConcurrentHashMap<String, Date> startTimes;
	
	/**
	 * 构造器
	 */
	public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		startTimes = new ConcurrentHashMap<>();
	}

	@Override
	public void shutdown() {
		System.out.println("MyExecutor: Going to shutdow.");
		//已执行过的任务数
		System.out.println("MyExecutor: Executed tasks: " + getCompletedTaskCount());
		//正在执行的任务数
		System.out.println("MyExecutor: Running tasks: " + getActiveCount());
		//等待执行的任务数
		System.out.println("MyExecutor: Pending tasks: " + getQueue().size());
		super.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		System.out.println("MyExecutor: Going to immediately shutdown.");
		//已执行过的任务数
		System.out.println("MyExecutor: Executed tasks: " + getCompletedTaskCount());
		//正在执行的任务数
		System.out.println("MyExecutor: Running tasks: " + getActiveCount());
		//等待执行的任务数
		System.out.println("MyExecutor: Pending tasks: " + getQueue().size());
		return super.shutdownNow();
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		//输出将要执行的线程名字，任务的哈希码
		System.out.println("MyExecutor: A task is beginning: " + t.getName() + ": " + r.hashCode());
		//开始日期存放到HashMap中，哈希码作为键
		startTimes.put(String.valueOf(r.hashCode()), new Date());
		//super.beforeExecute(t, r);
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		Future<?> result = (Future<?>) r;
		try {
			System.out.println("******************************");
			System.out.println("MyExecutor: A task is finishing.");
			//输出任务的执行结果
			System.out.println("MyExecutor: Result: " + result.get());
			Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
			Date finishDate = new Date();
			long diff = finishDate.getTime() - startDate.getTime();
			//输出任务运行的时间毫秒数
			System.out.println("MyExecutor: Duration: " + diff);
			System.out.println("******************************");
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//super.afterExecute(r, t);
	}

	
}
