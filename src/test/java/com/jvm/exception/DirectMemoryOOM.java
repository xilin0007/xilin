package com.jvm.exception;

/**
 * @Description TODO 直接内存OOM
 * 设置堆内存最大20M，直接内存最大10M
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 * @author fangxilin
 * @date 2017年11月15日
 */
public class DirectMemoryOOM {
	
	private static final int _1MB = 1024 * 1024;

	public static void main(String[] args) {
		

	}

}
