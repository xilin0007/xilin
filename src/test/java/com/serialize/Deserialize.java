package com.serialize;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class Deserialize {
	
	/**
	 * 反序列化
	 * @createTime 2017年11月8日,下午2:29:20
	 * @createAuthor fangxilin
	 * @param sf 实例序列化后的byte数组
	 * @return
	 */
	public static Object deserialize(byte[] sf) {
		try {
			InputStream is = new ByteArrayInputStream(sf);
			ObjectInputStream ois = new ObjectInputStream(is);
			return ois.readObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 序列化
	 */
	public static void serialize(Object o) {
		try {
			OutputStream os = new FileOutputStream("c//a.dat");
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(o);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
