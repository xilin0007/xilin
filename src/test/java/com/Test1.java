package com;

import java.io.File;

import org.apache.commons.io.FileUtils;




public class Test1 {
	
	public static void main(String[] args) {
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", 1);
		param.put("name", null);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(param);
		
		String jsonString = JSON.toJSONString(list);
		System.out.println(jsonString);*/
		
		/** 最后将临时文件删除 **/
    	File file = new File("e:/二维码.jpg");
    	boolean ret = FileUtils.deleteQuietly(file);
    	System.out.println(ret);
	}

}
