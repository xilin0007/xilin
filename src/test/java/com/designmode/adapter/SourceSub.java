package com.designmode.adapter;

/**
 * 接口的适配器模式
 * 继承适配器类，而不是直接implements接口，只需重写需要用到的方法
 */
public class SourceSub extends Adapter {

	@Override
	public void method1() {
		System.out.println("the sourceable interface's first Sub1!");
	}
}
