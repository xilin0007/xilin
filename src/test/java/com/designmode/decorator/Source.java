package com.designmode.decorator;
/**
 * 被装饰类
 * @author fangxilin
 * @date 2018年2月7日
 */
public class Source implements Sourceable {

	@Override
	public void method() {
		System.out.println("the original method!");
	}

}
