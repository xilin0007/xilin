package com.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * ForkJoinPool
 * 任务分解
 * @Description TODO
 * @author fangxilin
 * @date 2017-3-29
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class ForkJoinPoolTest {
	public static void main(String[] args) {
		ProductListGenerator generator = new ProductListGenerator();
		List<Product> products = generator.generate(1000);
		Task task = new Task(products, 0, products.size(), 0.20);
		//创建一个ForkJoinPool线程池
		ForkJoinPool pool = new ForkJoinPool();
		pool.execute(task);
		//打印线程池演变信息，直到任务结束
		do {
			System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
			System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
			System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!task.isDone());
		//关闭线程池
		pool.shutdown();
		
		//检查任务是否已完成并没有错误
		
	}
}

//产品类
class Product {
	private String name;
	private double price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}

//产品列表生成类
class ProductListGenerator {
	public List<Product> generate(int size) {
		List<Product> ret = new ArrayList<Product>();
		for (int i = 0; i < size; i++) {
			Product product = new Product();
			product.setName("Product-" + i);
			product.setPrice(10);
			ret.add(product);
		}
		return ret;
	}
}

//继承RecursiveAction：用于任务没有返回结果的场景
class Task extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private List<Product> products;
	private int first;
	private int last;
	private double increment;//产品价格的增加额
	
	public Task(List<Product> products, int first, int last, double increment) {
		super();
		this.products = products;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}
	
	//任务的执行逻辑
	@Override
	protected void compute() {
		//如果相差小于10，调用updatePrices()
		if (last - first < 10) {
			updatePrices();
		} else {
			int middle = (last + first) / 2;
			System.out.printf("Task: Pending tasks: %s\n", getQueuedTaskCount());
			//分解为两部分处理
			Task t1 = new Task(products, first, middle + 1, increment);
			Task t2 = new Task(products, middle + 1, last, increment);
			//执行两个新任务
			invokeAll(t1, t2);
		}
	}
	
	//更新处于firs和last属性之间的产品
	private void updatePrices() {
		for (int i = first; i < last; i++) {
			Product product = products.get(i);
			product.setPrice(product.getPrice() * (1 + increment));
		}
	}
	
}