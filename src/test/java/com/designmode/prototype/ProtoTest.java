package com.designmode.prototype;

import java.io.IOException;

/**
 * 深复制和浅复制的区别：
 * 	浅复制不会复制对象下的域，深复制是完全复制
 */
public class ProtoTest {

	public static void main(String[] args) {
		Prototype source = new Prototype();
		SerializableObject obj = new SerializableObject(1);
		System.out.println("原始值：" + source + "，原始值obj：" + obj);
		source.setString("aaa");
		source.setObj(obj);
		try {
			//浅复制
			Prototype clone1 = source.clone();
			System.out.println("浅复制：" + clone1 + "，浅复制obj：" + clone1.getObj());
			//深复制
			Object clone2 = source.deepClone();
			System.out.println("深复制：" + clone2 + "，深复制obj：" + ((Prototype) clone2).getObj());
		} catch (CloneNotSupportedException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
