package com.jvm.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO 运行时常量池OOM异常
 * 设置方法区（jdk1.6之前运行时常量池是方法区的一部分）大小
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author fangxilin
 * @date 2017年11月14日
 */
public class RuntimeConstPoolOOM {

	public static void main(String[] args) {
		//使用List保持着常量池的引用，避免Full GC回收常量池行为
		List<String> list = new ArrayList<>();
		//10M的PermSize在Integer范围内足够产生OOM了
		int i = 0;
		while (true) {
			/**
			 * intern()方法是个native方法，如果字符串常量池中已经包含该字符串，就返回该String对象，
			 * 否则（首次出现）将该字符串添加到常量池，并返回该引用
			 */
			list.add(String.valueOf(i++).intern());
		}

	}

}
