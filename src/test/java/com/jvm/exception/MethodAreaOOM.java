package com.jvm.exception;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @Description TODO 借助CGLib使方法区OOM
 * 设置方法区大小
 * VM Args: -XX:PermSize=2M -XX:MaxPermSize=2M
 * @author fangxilin
 * @date 2017年11月14日
 */
public class MethodAreaOOM {

	static class OOMObject {}
	
	public static void main(String[] args) {
		while (true) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
					return arg3.invokeSuper(arg0, arg2);
				}
			});
			enhancer.create();
		}

	}

}
