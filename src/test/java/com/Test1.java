package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;



public class Test1 {
	
	public static void main(String[] args) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", 1);
		param.put("name", null);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(param);
		
		String jsonString = JSON.toJSONString(list);
		System.out.println(jsonString);
		
	}

}
