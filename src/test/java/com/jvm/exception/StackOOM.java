package com.jvm.exception;

/**
 * @Description TODO java栈OOM
 * java虚拟机栈参数大小为2M（可以设置大些，更容易出现OOM）
 * VM Args: -Xss2m
 * @author fangxilin
 * @date 2017年11月14日
 */
public class StackOOM {
	
	private void dontStop() {
		while (true) {
			
		}
	}
	
	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontStop();
					
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) throws Throwable {
		StackOOM oom = new StackOOM();
		oom.stackLeakByThread();
	}

}
