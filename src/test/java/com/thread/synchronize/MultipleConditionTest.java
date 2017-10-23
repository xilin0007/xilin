package com.thread.synchronize;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 在锁中使用多条件
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-27
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class MultipleConditionTest {
	public static void main(String[] args) {
		FileMock mock = new FileMock(100, 10);
		Buffer buffer = new Buffer(20);
		Producer2 producer = new Producer2(mock, buffer);
		Thread threadProducer = new Thread(producer, "Producer");
		Consumer2[] consumers = new Consumer2[3];
		Thread[] threadConsumers = new Thread[3];
		for (int i = 0; i < 3; i++) {
			consumers[i] = new Consumer2(buffer);
			threadConsumers[i] = new Thread(consumers[i], "Consumer " + i);
		}
		//启动线程
		threadProducer.start();
		for (int i = 0; i < 3; i++) {
			threadConsumers[i].start();
		}
	}
	
}

//文本模拟器类
class FileMock {
	private String[] content;//内容
	private int index;//行号
	public FileMock(int size, int length) {
		this.content = new String[size];
		for (int i = 0; i < size; i++) {
			StringBuilder builder = new StringBuilder(length);
			for (int j = 0; j < length; j++) {
				int indice = (int) (Math.random() * 255);
				builder.append((char) indice);
			}
			this.content[i] = builder.toString();
		}
		this.index = 0;
	}
	//是否还有可以处理的数据
	public boolean hasMoreLines() {
		return index < content.length;
	}
	//返回index指定的行内容
	public String getLine() {
		if (this.hasMoreLines()) {
			System.out.println("Mock: " + (content.length - index));
			return content[index++];
		}
		return null;
	}
}

//数据缓冲类
class Buffer {
	private ReentrantLock lock;//对修改buffer的代码块进行控制
	private Condition lines;
	private Condition space;
	
	private LinkedList<String> buffer;//存放共享数据
	private int maxSize;//buffer的长度
	private boolean pendingLines;//缓冲区是否还有数据
	public Buffer(int maxSize) {
		this.lock = new ReentrantLock();
		this.lines = lock.newCondition();
		this.space = lock.newCondition();
		this.buffer = new LinkedList<>();
		this.maxSize = maxSize;
		this.pendingLines = true;
	}
	
	public void insert(String line) {
		lock.lock();
		try {
			while (buffer.size() == maxSize) {
				space.await();//等待空位出现
			}
			buffer.offer(line);//添加内容
			System.out.printf("%s: Inserted Line: %d\n", Thread.currentThread().getName(), buffer.size());
			lines.signalAll();//唤醒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public String get() {
		String line = null;
		lock.lock();
		try {
			while (buffer.size() == 0 && !hasPendingLines()) {
				lines.await();//等待空位出现
			}
			if (hasPendingLines()) {
				line = buffer.poll();//移除掉第一个元素
				System.out.printf("%s: Line Readed: %d\n", Thread.currentThread().getName(), buffer.size());
				space.signalAll();//唤醒
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return line;
	}
	
	public void setPendingLines(boolean pendingLines) {
		this.pendingLines = pendingLines;
	}

	public boolean hasPendingLines() {
		return pendingLines || buffer.size() > 0;
	}
}

//生产类--生成数据缓冲
class Producer2 implements Runnable{
	
	private FileMock mock;
	private Buffer buffer;
	
	public Producer2(FileMock mock, Buffer buffer) {
		this.mock = mock;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		buffer.setPendingLines(true);
		while (mock.hasMoreLines()) {
			String line = mock.getLine();//获取100-0行的内容
			buffer.insert(line);
		}
		buffer.setPendingLines(false);
	}
}

//消费类--取出数据缓冲
class Consumer2 implements Runnable{
	private Buffer buffer;
	public Consumer2(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (buffer.hasPendingLines()) {
			String line = buffer.get();
			processLine(line);
		}
	}
	
	private void processLine(String line) {
		try {
			Random random = new Random();
			Thread.sleep(random.nextInt(100));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}