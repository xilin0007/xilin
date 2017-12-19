package com.jvm.classload;
import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {
	
	public static void main(String[] args) throws Exception {
		//创建自定义的类加载器
		ClassLoader myloader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					//InputStream is = getSystemClassLoader().getResourceAsStream(fileName);//系统类加载器
					if (is == null) {
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				} catch (IOException e) {
					throw new ClassNotFoundException(name);
				}
			}
			
		};
		Object obj = myloader.loadClass("com.jvm.classload.ClassLoaderTest").newInstance();
		System.out.println(obj.getClass());
		//自定义的类加载器加载的类与默认的系统类加载器加载的类不相等
		System.out.println(obj instanceof ClassLoaderTest);
	}

}
