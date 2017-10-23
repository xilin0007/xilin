package com.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * 不同的行为与枚举常量关联起来，特定于常量方法的实现
 */
public enum Operation {
	PLUS("+", "加法") {
		double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-", "减法") {
		double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*", "乘法") {
		double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/", "除法") {
		@Override
		double apply(double x, double y) {
			return x / y;
		}
	};
	
	/**
	 * 枚举中的抽象方法必须会被常量中的具体方法覆盖
	 */
	abstract double apply(double x, double y);
	
	private String value;
	private String name;
	
	private Operation(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static void main(String[] args) {
		double apply = Operation.PLUS.apply(1, 5);
		System.out.println(apply);
		
		Operation plus = Operation.valueOf("PLUS");//Operation.PLUS
		System.out.println(plus);
		
		//序数
		int index = Operation.DIVIDE.ordinal();
		System.out.println(index);
		
		//EnumSet集合
		EnumSet<Operation> eset = EnumSet.of(Operation.PLUS, Operation.MINUS);
		for (Operation op : eset) {
			System.out.print(op + " ");
		}
		
		//EnumMap集合
		Map<Operation, Object> emap = new EnumMap<Operation, Object>(Operation.class);
		emap.put(Operation.PLUS, "++");
	}
	
}
