package com.jvm.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {
	
	interface Ihello {
		void sayHello();
	}
	
	static class Hello implements Ihello {
		@Override
		public void sayHello() {
			System.out.println("hellow world");
		}
	}
	
	/**
	 * 代理类
	 */
	static class DynamicProxy implements InvocationHandler {
		
		Object originalObj;
		
		Object bind(Object originalObj) {
			this.originalObj = originalObj;
			return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("welcome");//代理新增的操作
			return method.invoke(originalObj, args);
		}
		
	}
	
	public static void main(String[] args) {
		Ihello hello = (Ihello) new DynamicProxy().bind(new Hello());
		hello.sayHello();
	}

}
