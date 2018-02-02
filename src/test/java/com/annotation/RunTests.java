package com.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

	public static void main(String[] args) throws Exception {
		//sampleTest();
		//sample2Test();
		sample3Test();
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
	
	public static void sample3Test() throws Exception {
		/**
		 * 获取类注解
		 */
		Class clazz = Sample3.class;
		clazz.getAnnotation(MyTestA.class);//只获取@MyTestA注解
		Annotation[] annotations = clazz.getAnnotations();//获取所有注解
		System.out.println("#############类注解：############：");
		for (Annotation annotation : annotations) {
			MyTestA testA = (MyTestA) annotation;
			System.out.println("id=" + testA.id() + ", name=" + testA.name() + ", gid=" + testA.gid());
		}
		
		/**
		 * 获取类方法注解
		 */
		Method[] methods = clazz.getDeclaredMethods();
		System.out.println("#############类方法注解：############：");
		for (Method method : methods) {
			boolean hasAnnotation = method.isAnnotationPresent(MyTestA.class);//是否存在@MyTestA注解
			if (hasAnnotation) {
				MyTestA testA = method.getAnnotation(MyTestA.class);
				System.out.println("method=" + method.getName() + ", id=" + testA.id() + ", name=" + testA.name() + ", gid=" + testA.gid());
			}
		}
		
		/**
		 * 获取构造方法注解
		 */
		Constructor[] constructors = clazz.getConstructors();
		System.out.println("#############构造方法注解：############：");
		for (Constructor constructor : constructors) {
			boolean hasAnnotation = constructor.isAnnotationPresent(MyTestA.class);//是否存在@MyTestA注解
			if (hasAnnotation) {
				MyTestA testA = (MyTestA) constructor.getAnnotation(MyTestA.class);
				System.out.println("constructor=" + constructor .getName() + ", id=" + testA.id() + ", name=" + testA.name() + ", gid=" + testA.gid());
			}
		}
	}
	
}
