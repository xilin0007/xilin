package com.jvm.classload.cases;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 执行远程服务器字节码文件
 * @author fangxilin
 * @date 2017年12月15日
 */
public class ExecuteTest {
	
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("E:\\workspace2\\template\\target\\test-classes\\com\\jvm\\classload\\cases\\TestClass.class");
			byte[] b = new byte[is.available()];
			is.read(b);
			is.close();
			
			System.out.println(JavaClassExecuter.execute(b));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
