package com;

import java.io.IOException;

import com.fxl.frame.util.FunctionUtils;




public class Test1 {
	
	public static void main(String[] args) throws IOException {
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
		
		/*String filePath = "G:/media/Desktop/二维码.jpg";
		String decode = GenerateQRCode.decode(filePath);
		System.out.println(decode);*/
		
		/*String url = "http://192.168.0.2:8080/nutritionV2/sport/listSportsByName";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("query", "步");
		HttpClient httpClient = new HttpClient(url, params);
		String ret = "";
		try {
			ret = httpClient.post();
			System.out.println(ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*String html = StringEscapeUtils.escapeHtml("<a>dddd</a>");
		System.out.println(html);
		
		String str = StringEscapeUtils.escapeJava("中国");
		System.out.println(str);*/
		double bmi = FunctionUtils.getBMI(170, 57);
		System.out.println(bmi);
	}
	
}
