package com.enums;

interface IOperation {
	double apply(double x, double y);
}

/**
 * 枚举实现接口
 */
public enum BasicOperation implements IOperation {
	PLUS("+", "加法"),
	MINUS("-", "减法"),
	TIMES("*", "乘法"),
	DIVIDE("/", "除法");
	
	private String value;
	private String name;
	
	BasicOperation(String value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public double apply(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

}
