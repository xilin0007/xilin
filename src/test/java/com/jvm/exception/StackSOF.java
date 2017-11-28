package com.jvm.exception;
/**
 * @Description TODO java栈SOF
 * java虚拟机栈参数大小为128k
 * VM Args: -Xss128k
 * @author fangxilin
 * @date 2017年11月14日
 */
public class StackSOF {
	
	private int stackLength = 1;
	
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	
	public static void main(String[] args) throws Throwable {
		StackSOF oom = new StackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("Stack length:" + oom.stackLength);
			throw e;
		}
		
	}
}
