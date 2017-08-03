package com.designmode.singleton;

public class Singleton {
	
	/** 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */ 
	private static Singleton instance = null;
	
	/** 私有构造方法，防止被实例化 */ 
	private Singleton() {}
	
	/** 
	 * 静态工程方法，创建实例，
	 * 不为空的话直接返回，防止创建多个实例 ，
	 */
	public static Singleton getInstance() {
		if (instance == null) {
			/**
			 * 防止多线程的环境下创建多个实例
			 * 只有在第一次创建对象的时候需要加锁，之后就不需要了
			 */
			synchronized (Singleton.class) {
				if (instance == null) {
					//可能JVM会为新的Singleton实例分配空间，然后直接赋值给instance成员
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
	
	/** 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */  
    public Object readResolve() {  
        return instance;  
    }
}
