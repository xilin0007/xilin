package com.demo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 循环Map的4种方式
 * @author fangxilin
 * @date 2018年2月1日
 */
public class MapLoop {

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("China", "中国");
		map.put("USA", "美国");
		/**
		 * 方式一 这是最常见的并且在大多数情况下也是最可取的遍历方式。在键值都需要时使用。
		 */
		System.out.println("#############方式一############：");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
		/**
		 * 方式二使用Iterator遍历
		 */
		System.out.println("#############方式二############：");
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		
		/**
		 * 方式三 如果只需要map中的键或者值 在for-each循环中遍历keys或values,
		 */
		System.out.println("#############方式三############：");
		for (String key : map.keySet()) {//遍历map中的键 
			System.out.println("Key = " + key);
		}
		for (Object value : map.values()) {
			System.out.println("Value = " + value);
		}
		
		/**
		 * 方式四 通过键找值遍历（效率低）
		 */
		System.out.println("#############方式四############：");
		for (String key : map.keySet()) {//遍历map中的键
			Object value = map.get(key); 
			System.out.println("Key = " + key + ", Value = " + value);
		}
	}

}
