package com.designmode.strategy;

/**
 * 策略B
 */
public class ConcreteStrategyB implements Strategy {

	@Override
	public void algorithm() {
		System.out.println("采用策略B计算");
	}

}
