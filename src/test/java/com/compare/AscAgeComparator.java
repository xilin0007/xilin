package com.compare;

import java.util.Comparator;

/**
 * 实现Comparator
 */
public class AscAgeComparator implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		return p1.getAge() - p2.getAge();
	}
}