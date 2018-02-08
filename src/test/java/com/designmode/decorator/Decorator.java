package com.designmode.decorator;
/**
 * 装饰类，可为Source类动态的添加一些功能
 * @author fangxilin
 * @date 2018年2月7日
 */
public class Decorator implements Sourceable {
	
	/**
	 * 持有被装饰对象的实例
	 */
	private Sourceable source;

	public Decorator(Sourceable source) {
		super();
		this.source = source;
	}

	/**
	 * 被装饰的方法
	 */
	@Override
	public void method() {
		System.out.println("before decorator!");
		source.method();
		System.out.println("after decorator!");
	}
	
}
