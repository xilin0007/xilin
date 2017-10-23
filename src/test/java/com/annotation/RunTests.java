package com.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

	public static void main(String[] args) throws Exception {
		//sampleTest();
		sample2Test();
	}
	
	public static void sampleTest() throws Exception {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName("com.annotation.Sample");
		for (Method m : testClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(MyTest.class)) {
				tests++;
				try {
					m.invoke(null);
					passed++;
				} catch (InvocationTargetException e) {
					Throwable exc = e.getCause();
					System.out.println(m + " failed: " + exc);
				} catch (Exception e) {
					System.out.println("INVALID @MyTest: " + m);
				}
			}
		}
		System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
	}
	
	public static void sample2Test() throws Exception {
		int tests = 0;
		int passed = 0;
		Class<?> testClass = Class.forName("com.annotation.Sample2");
		for (Method m : testClass.getDeclaredMethods()) {
			if (m.isAnnotationPresent(ExceptionTest.class)) {
				tests++;
				try {
					m.invoke(null);
					System.out.printf("Test %s failed: no exception%n", m);
				} catch (InvocationTargetException e) {
					Throwable exc = e.getCause();
					//获取方法注解的值
					Class<? extends Exception> excType = m.getAnnotation(ExceptionTest.class).value();
					if (excType.isInstance(exc)) {
						passed++;//只有m1抛出了ArithmeticException，所以通过
					} else {
						System.out.printf("Test %s failed: expected %s, got %s%n", m, excType.getName(), exc);
					}
				} catch (Exception e) {
					System.out.println("INVALID @MyTest: " + m);
				}
			}
		}
		System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
	}
	
}
