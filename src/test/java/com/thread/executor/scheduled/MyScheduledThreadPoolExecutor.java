package com.thread.executor.scheduled;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

	public MyScheduledThreadPoolExecutor(int corePoolSize) {
		super(corePoolSize);
	}

	@Override
	protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
		MyScheduledTask<V> myTask = new MyScheduledTask<>(runnable, null, task, this);
		return myTask;
		//return super.decorateTask(runnable, task);
	}

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		ScheduledFuture<?> task = super.scheduleAtFixedRate(command, initialDelay, period, unit);
		MyScheduledTask<?> myTask = (MyScheduledTask<?>) task;
		//设置任务的周期
		myTask.setPeriod(TimeUnit.MILLISECONDS.convert(period, unit));
		return task;
	}
	
}
