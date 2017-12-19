package com.jvm.gc;

/**
 * @Description 引用计数算法
 * 不能解决对象间互相循环引用的问题
 * @author fangxilin
 * @date 2017年11月28日
 */
public class ReferenceCountingGC {

	public Object instance = null;
	
	private static final int _1MB = 1024 * 1024;
	
	private byte[] bigSize = new byte[2 * _1MB];
	
	public static void testGC() {
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		objA.instance = objB;
		objB.instance = objA;
		
		//假设在这行发生GC，引用计数算法objA和objectB不能被回收
		System.gc();
	}
	
	public static void main(String[] args) {
		testGC();
	}

}
