package com.thread.executor.pool;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 实现Callable接口，可返回结果
 * 实现Runnable接口，不返回结果
 * @author fangxilin
 * @date 2018年2月6日
 */
public class SleepTwoSecondsTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		//休眠2s
		TimeUnit.SECONDS.sleep(2);
		return new Date().toString();
	}

}
