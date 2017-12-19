package com.jvm.gc;

/**
 * @Description 长期存活的对象进入老年代
 * 对象年龄计数器到了设置的值后就会进入老年代
 * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * @author fangxilin
 * @date 2017年11月29日
 */
public class TenuringThresholdTest {

	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		byte[] b1, b2, b3;
		b1 = new byte[_1MB / 4];
		b2 = new byte[4 * _1MB];
		b3 = new byte[4 * _1MB];
		b3 = null;
		b3 = new byte[4 * _1MB];
	}

}
