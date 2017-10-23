package com.serialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public final class Period implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Period INSTANCE = new Period(new Date(), new Date());
	
	private final Date start;
	private final Date end;
	
	public Period(Date start, Date end) {
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());
		//先拷贝在校验，防止校验后拷贝期间被其他线程篡改引用值
		if (this.start.compareTo(this.end) > 0) {
			throw new IllegalArgumentException(start + " after " + end);
		}
	}
	
	public Date start() {
		return new Date(start.getTime());
	}
	public Date end() {
		return new Date(end.getTime());
	}
	
	/**自定义的反序列化*/
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();//默认的反序列化
		//保护性拷贝
		Date sta = new Date(start.getTime());
		Date e = new Date(end.getTime());
		if (sta.compareTo(e) > 0) {
			throw new IllegalArgumentException(start + " after " + end);
		}
	}
	
	/**反序列化后readResolve会被调用，并忽略了被反序列化的对象，只返回INSTANCE*/
	private Object readResolve() {
		return INSTANCE;
	}
	
	
	/**序列化代理模式，待实现。。。*/
	private static class SerializationProxy implements Serializable {
		
	}
}
