package com.reflect;

import java.lang.reflect.Method;

public class InvokeTest {

	public int add(int param1, int param2) {
		return param1 + param2;
	}

	public static String echo(String mesg) {
		return "echo " + mesg;
	}
	
	public static void main(String[] args) throws Exception {
		Class<?> classType = InvokeTest.class;
		//Class<?> classType = Class.forName("com.reflect.InvokeTest");
		Object invokertester = classType.newInstance();
		
		/**
		 * Method类的invoke(Object obj,Object args[])方法接收的参数必须为对象，
		 * 如果参数为基本类型数据，必须转换为相应的包装类型的对象。invoke()方法的返回值总是对象，
		 * 如果实际被调用的方法的返回类型是基本类型数据，那么invoke()方法会把它转换为相应的包装类型的对象，再将其返回
		 */
		Method addMethod = classType.getMethod("add", new Class[]{int.class, int.class});
		Object result = addMethod.invoke(invokertester, new Object[]{1, 2});
		System.out.println(result);

		Method echoMethod = classType.getMethod("echo", new Class[] { String.class });
		result = echoMethod.invoke(invokertester, "hello");
		System.out.println(result);
	}

}
