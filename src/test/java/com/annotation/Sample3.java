package com.annotation;

import java.util.HashMap;
import java.util.Map;

@MyTestA(name = "type", gid = Long.class)//类注解
public class Sample3 {
	
	@MyTestA(name = "param", id = 1, gid = Long.class)//类成员注解
	private Integer age;
	
	@MyTestA(name = "construct", id = 2, gid = Long.class) // 构造方法注解
	public Sample3() {}
	
	@MyTestA(name = "public method", id = 3, gid = Long.class) // 类方法注解
	public void a() {
		Map<String, Object> m = new HashMap<>(0);
	}
	
}
