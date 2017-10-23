package com.genericity;

import java.util.HashMap;
import java.util.Map;

/**
 * 有限考虑类型安全的异构容器
 */
public class Favorites {
	
	private Map<Class<?>, Object> favorites = new HashMap<>();
	
	/**
	 * 保存最喜欢的类的实例
	 */
	public <T> void putFavorite(Class<T> type, T instance) {
		if (type == null) {
			throw new NullPointerException("Type is null");
		}
		//favorites.put(type, instance);
		favorites.put(type, type.cast(instance));
	}
	
	/**
	 * 获取最喜欢的类的实例
	 */
	public <T> T getFavorite(Class<T> type) {
		return type.cast(favorites.get(type));
	}
	
	public static void main(String[] args) {
		Favorites f = new Favorites();
		f.putFavorite(String.class, "Java");
		f.putFavorite(Integer.class, 0xcafebabe);
		f.putFavorite(Class.class, Favorites.class);
		String favoriteString = f.getFavorite(String.class);
		int favoriteInteger = f.getFavorite(Integer.class);
		Class<?> favoriteClass = f.getFavorite(Class.class);
		System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass);
	}
}
