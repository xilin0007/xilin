package com.serialize;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;

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
}
