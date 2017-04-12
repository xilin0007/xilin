package com;

import com.enums.ConsultStatusEnums;

public class Test {
	public static void main(String[] args) {
		/*String property = System.getProperty("java.io.tmpdir");
		System.out.println(property);*/
		
		/*String[] ss = {"aa", "bb"};
		int i = 0;
		System.out.println(ss[i++]);*/
		
		System.out.println(ConsultStatusEnums.getStatusName(5));
	}
}
