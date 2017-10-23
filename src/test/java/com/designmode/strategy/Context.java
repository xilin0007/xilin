package com.designmode.strategy;

public class Context {
	
	Strategy strategy;

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public void method() {
		strategy.algorithm();
	}
	
}
