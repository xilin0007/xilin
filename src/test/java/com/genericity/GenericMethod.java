package com.genericity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 泛型方法
 */
public class GenericMethod {
	
	/**
	 * 合并集合
	 */
	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> result = new HashSet<E>(s1);
		result.addAll(s2);
		return result;
	}
	
	/**
	 * 求出集合中最大值
	 */
	public static<T extends Comparable<? super T>> T max(List<? extends T> list) {
		Iterator<? extends T> i = list.iterator();
		T result = i.next();
		while (i.hasNext()) {
			T t = i.next();
			if (t.compareTo(result) > 0) {
				result = t;
			}
		}
		return result;
	}
	
	/**
	 * 共有方法，交换两个参数，通配符用作公共API
	 */
	public static void swap(List<?> list, int i, int j) {
		swapHelper(list, i, j);
	}
	
	/**
	 * 私有方法，交换两个参数，参数类型E
	 * 
	 */
	private static <E> void swapHelper(List<E> list, int i, int j) {
		list.set(i, list.set(j, list.get(i)));
	}
	
	public static void main(String[] args) {
		Set<Integer> is = new HashSet<>();
		Set<Double> ds = new HashSet<>();
		//Set<Number> numbers = union(is, ds);//报错
		//显示的类型参数.<Number>
		Set<Number> numbers1 = GenericMethod.<Number>union(is, ds);
	}
}
