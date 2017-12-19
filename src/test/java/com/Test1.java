package com;

import java.awt.Color;

import com.fxl.frame.util.Const;
import com.fxl.frame.util.TimeUtils;




public class Test1 {
	
	public static void main(String[] args) throws Exception {
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
		
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("one", "cc");
		String str = "cc";
		int hashCode = map.hashCode();
		System.out.println("map：" + hashCode);
		System.out.println("map中cc：" + map.get("one").hashCode());
		System.out.println("str：" + str.hashCode());*/
		
		/*EnumMap<AppIdEnums, Object> enumMap = new EnumMap<>(AppIdEnums.class);
		enumMap.put(AppIdEnums.TS, "天使");*/
		
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("one", "aa");
		map.put("two", "bb");
		Set<String> keys = map.keySet();
		System.out.println("之前：" + keys.toString());
		keys.remove("one");
		//keys.add("three");
		System.out.println("之后：" + map.keySet().toString());*/
		
		/*ColorPoint a = new ColorPoint(1, 2, Color.RED);
		Point b = new Point(1, 2);
		ColorPoint c = new ColorPoint(1, 2, Color.BLUE);
		System.out.println(b.equals(a));*/
		
		/*AtomicInteger counter = new AtomicInteger();
		counter.incrementAndGet();
		System.out.println(counter.get());*/
	    System.out.println("Format To String(Date):" + TimeUtils.timestampToString(1513612800000L, Const.YYYYMMDD_HHMMSS));
		
		
	}
	
}

class Point implements Cloneable {
	
	private final int x;
	private final int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		Point p = (Point) obj;
		return p.x == x && p.y == y;
	}
	
}
class ColorPoint extends Point {
	
	private final Color color;
	
	public ColorPoint(int x, int y, Color color) {
		super(x, y);
		this.color = color;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		if (!(obj instanceof ColorPoint)) {
			return obj.equals(this);
		}
		//ColorPoint c = (ColorPoint) obj;
		return super.equals(obj) && ((ColorPoint) obj).color == color;
	}
}

