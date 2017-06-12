package com;

import com.fxl.frame.util.GenerateQRCode;

public class Test1 {
	
	public static void main(String[] args) {
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", 1);
		param.put("name", null);
		
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(param);
		
		String jsonString = JSON.toJSONString(list);
		System.out.println(jsonString);*/
		
		/*String filePath = GenerateQRCode.generate("https://www.baidu.com");
		System.out.println(filePath);*/
		
		/*String targetFileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println(targetFileName);*/
		
		/*String encodeUrl = URLEncoder.encode("http://hospital.jumper-health.com/new_weight/base/login", "UTF-8");
		System.out.println(encodeUrl);*/
		
		String filePath = "G:/media/Desktop/二维码.jpg";
		String decode = GenerateQRCode.decode(filePath);
		System.out.println(decode);

    	
	}

}
