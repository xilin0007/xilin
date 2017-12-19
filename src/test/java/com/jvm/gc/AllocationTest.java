package com.jvm.gc;

/**
 * @Description 对象内存分配测试，优先在Eden区分配
 * 设置java堆大小为20MB，其中10M分配给新生代，剩下的给老年代，新生代中Eden区与Survivor区比例是8:1，同时打印GC日志
 * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author fangxilin
 * @date 2017年11月29日
 */
public class AllocationTest {
	
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		byte[] b1, b2, b3, b4;
		b1 = new byte[2 * _1MB];
		b2 = new byte[2 * _1MB];
		b3 = new byte[2 * _1MB];
		b4 = new byte[4 * _1MB];//出现异常Minor GC
	}
}
