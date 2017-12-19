package com.jvm.methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @Description Method Handle（方法指针）基础用法演示，掌控方法分派规则
 * @date 2017年12月12日
 */
public class MethodHandleTest {
	
	public static class ClassA {
		public void println(String s) {
			System.out.println(s);
		}
	}
	
	/**
	 * 模拟invokevirtual指令执行过程，方法分派
	 */
	private static MethodHandle getPrintlnMH(Object reveiver) throws NoSuchMethodException, IllegalAccessException {
		/**
		 * MethodType：代表方法类型，第一个参数表示方法的返回值，第二个及以后的参数表示具体入参
		 */
		MethodType mt = MethodType.methodType(void.class, String.class);
		/**
		 * findVirtual，指定类中查找符合给定的方法名称，方法类型，方法句柄，
		 * 因为调用的是一个虚方法，第一个参数是隐式的this，所以通过bindTo方法传递this入参
		 */
		
		return MethodHandles.publicLookup().findVirtual(reveiver.getClass(), "println", mt).bindTo(reveiver);
	}
	
	public static void main(String[] args) throws Throwable {
		Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();
		getPrintlnMH(obj).invokeExact("dfasdlkfjdkf");
	}

}
