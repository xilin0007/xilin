package com.designmode.decorator;

public class Main {

	public static void main(String[] args) {
		Sourceable source = new Source();
		Sourceable decorator = new Decorator(source);
		decorator.method();
	}

}
