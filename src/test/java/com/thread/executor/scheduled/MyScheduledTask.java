package com.thread.executor.scheduled;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程任务必须实现RunnableScheduledFuture接口
 * @author fangxilin
 * @date 2018年2月6日
 */
public class MyScheduledTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {
	
	private RunnableScheduledFuture<V> task;
	
	private ScheduledThreadPoolExecutor executor;
	
	private long period;
	
	private long startDate;
	
	public MyScheduledTask(Runnable runnable, V result, RunnableScheduledFuture<V> task, ScheduledThreadPoolExecutor executor) {
		super(runnable, result);
		this.task = task;
		this.executor = executor;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		if (!isPeriodic()) {//不是周期性任务
			return task.getDelay(unit);
		} else {
			if (startDate == 0) {
				return task.getDelay(unit);//返回task属性中的延迟值
			} else {
				//startDate和当前时间的差作为延迟值
				long delay = startDate - new Date().getTime();
				return unit.convert(delay, TimeUnit.MILLISECONDS);
			}
		}
	}

	@Override
	public int compareTo(Delayed o) {
		return task.compareTo(o);
	}

	@Override
	public boolean isPeriodic() {
		return task.isPeriodic();
	}

	@Override
	public void run() {
		if (isPeriodic() && (!executor.isShutdown())) {
			//是周期性任务，startDate需要更新为下一次任务执行的开始时间
			startDate = new Date().getTime() + period;
			executor.getQueue().add(this);
		}
		System.out.println("Pre-MyScheduledTask: " + new Date());
		System.out.println("MyScheduledTask: Is Periodic: " + isPeriodic());
		super.runAndReset();
		System.out.println("Post-MyScheduledTask: " + new Date());
	}

	public void setPeriod(long period) {
		this.period = period;
	}
}
