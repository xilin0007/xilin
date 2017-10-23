package com.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompareTest {

	public static void main(String[] args) {
		List<Person> list = new ArrayList<Person>();
		list.add(new Person("ccc", 20));
		list.add(new Person("AAA", 30));
		list.add(new Person("bbb", 10));
		list.add(new Person("ddd", 40));
		System.out.println("原始顺序：" + list.toString());
		Collections.sort(list);
		System.out.println("排序后的：" + list.toString());
		
		//按年龄正序排
		Collections.sort(list, new AscAgeComparator());
		System.out.println("年龄排序：" + list.toString());
	}
}

/**
 * 实现Comparable
 */
class Person implements Comparable<Person> {
	
	private int age;
	private String name;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Person o) {
		return name.compareTo(o.name);
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}
}