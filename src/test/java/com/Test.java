package com;

import java.util.ArrayList;
import java.util.List;


public class Test {
	public static void main(String[] args) {
		/*String property = System.getProperty("java.io.tmpdir");
		System.out.println(property);*/
		
		/*String[] ss = {"aa", "bb"};
		int i = 0;
		System.out.println(ss[i++]);*/
		
//		System.out.println(ConsultStatusEnums.getStatusName(5));
		
		List<VoTest> list = new ArrayList<VoTest>();
		List<Integer> ids = new ArrayList<Integer>(); 
		VoTest vo1 = new VoTest(1, "aaa");
		VoTest vo2 = new VoTest(2, "bbb");
		VoTest vo3 = new VoTest(3, "ccc");
		list.add(vo1);list.add(vo2);list.add(vo3);
		
		List<VoTest> listTemp = new ArrayList<VoTest>();
		VoTest votest = new VoTest();
		int a = 0;
		for (VoTest vo : list) {
//			votest.setId(vo.getId());
//			votest.setName(vo.getName());
			votest = vo;
			listTemp.add(votest);
//			ids.add(votest.getId());
			a = vo.getId();
			ids.add(a);
		}
		System.out.println(listTemp.toString());
		System.out.println(ids.toString());
		
	}
}

class VoTest {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public VoTest() {
	}
	public VoTest(int id, String name) {
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "VoTest [id=" + id + ", name=" + name + "]";
	}
}