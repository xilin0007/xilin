package com.jvm.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO java堆OOM
 * 设置虚拟机启动参数，java堆参数大小为20M
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author fangxilin
 * @date 2017年11月14日
 */
public class HeapOOM {
	
	static class OOMObject {}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		while (true) {
			list.add(new OOMObject());
		}
		
	}

}
