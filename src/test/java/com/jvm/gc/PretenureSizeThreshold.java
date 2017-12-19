package com.jvm.gc;

/**
 * @Description 大对象直接分配到老年代
 * 设置超过3MB的大对象直接进入老年代，只对Serial和ParNew收集器有效
 * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * @author fangxilin
 * @date 2017年11月29日
 */
public class PretenureSizeThreshold {

private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) {
		byte[] b1;
		b1 = new byte[4 * _1MB];//直接分配在老年代中
	}

}
