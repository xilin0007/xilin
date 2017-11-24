package com;

public class Test {
	public static void main(String[] args) {
		/*String property = System.getProperty("java.io.tmpdir");
		System.out.println(property);*/
		
		/*String[] ss = {"aa", "bb"};
		int i = 0;
		System.out.println(ss[i++]);*/
		
		//静态导入
		//String version = VERSION;
		
		TestException.method1();
		
	}
}

class TestException {
	
	public static void method1() {
		int num1 = 5;
		int x = 0, y = 1;
		try {
			y = 2;
			x = num1 / 0;
		} catch (Exception e) {
			throw e;
		}
		x++;
		System.out.println(y);
	}
	
	public static void method2() {
		int[] arr1 = {4, 5, 6};
		int[] arr2 = {7};
		for (int i : arr1) {
			System.out.println("arr1:" + i);
			int arr2Value = arr2[i];
			System.out.println("arr2:" + arr2Value);
		}
	}
	
}
