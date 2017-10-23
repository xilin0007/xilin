package com.fxl.frame.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.core.ReflectUtils;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.fxl.frame.util.file.FileUtils;


/**
 * http请求工具类
 */
public class HttpClient {
	protected static Logger logger = Logger.getLogger(HttpClient.class);
	/**
	 * 返回json的
	 */
	public final static String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
	/**
	 * 返回的普通请求
	 */
	public final static String CONTENT_TYPE_DEFAULT = "application/x-www-form-urlencoded;charset=UTF-8";

	Object params;
	String url;
	Map<String,String> headers;
	
	public HttpClient(String url) {
		this(url,null);
	}
	/**
     * @param url　提交的url
     * @param params　提交的参数 参数可以为Map<String,Object> 也可以是一个实体bean
	 */
	public HttpClient(String url, Object params) {
		this.headers = new HashMap<String, String>();
		this.params = params;
		this.url = url;
		if (url == null) {
			throw new NullPointerException("url");
		}
	}

	public enum Method{
		POST,GET;
	}
	
	private HttpURLConnection getUrlConnection(Method method) throws IOException {
		if (method == Method.GET) {
			String paramsStr = null;
			if (params != null && (paramsStr = getParamsStr(this.params)) != null) {
				if (url.lastIndexOf("?") > 0) {
					url = url + "&" + paramsStr;
				} else {
					url = url + "?" + paramsStr;
				}
				logger.info("the url is " + url);
			}
		}
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(this.url).openConnection();
		} catch (IOException e) {
			throw  e;
		}
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(5000);//连接超时 单位毫秒
		conn.setReadTimeout(5000);//读取超时 单位毫秒
		conn.setRequestProperty("Accept-Charset", "UTF-8");
		conn.setRequestProperty("contentEncoding", "UTF-8");
		conn.setRequestProperty("contentType", "utf-8");
	//	conn.setRequestProperty("Content-Type", CONTENT_TYPE_DEFAULT);
		conn.setRequestMethod(method.toString());
		for (Map.Entry<String, String> e : headers.entrySet()) {
			conn.addRequestProperty(e.getKey(), e.getValue());
		}
		if (method == Method.POST) {
			conn.setDoInput(true);
		}
		return conn;
	}
	
	/**
	 * post请求
	 * @throws IOException 
	 */
	public  String post() throws IOException {
	    HttpURLConnection conn = null;
		try {
			conn = getUrlConnection(Method.POST);
			String cType = 	conn.getRequestProperty("Content-Type");
			conn.connect();
			if (params != null) {
				// String paramsJoin = getParamsStr(params);
				String paramsStr = null;
				if (cType == null || CONTENT_TYPE_DEFAULT.contains(cType)) {
					paramsStr = getParamsStr(this.params);
				} else {
					JSONObject jsonObject = JSONObject.fromObject(this.params);
					paramsStr = jsonObject.toString();
				}
				logger.info("the params is " + paramsStr + ",url=" + this.url);
				if (paramsStr != null) {
					conn.getOutputStream().write(paramsStr.getBytes("UTF-8"));// 输入参数
				}
			}
		    int responseCode= conn.getResponseCode();
			if (responseCode == 200 || responseCode == 302) {
				InputStream ins = conn.getInputStream();
				return FileUtils.readStringFromStream(ins);
				// return new String(readInputStream(ins));
			}
			logger.error("responseCode:" + conn.getResponseCode() + ",url:"	+ url);
			if (responseCode == 500) {
				InputStream ins = conn.getErrorStream();
				logger.error(FileUtils.readStringFromStream(ins));
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}
	
	/**
	 * get请求
	 * @throws IOException 
	 */
	public  String get() throws IOException {
		HttpURLConnection conn = null;
		InputStream ins = null;
		try {
			conn = getUrlConnection(Method.GET);
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == 200 || responseCode == 302) {
				ins = conn.getInputStream();
				return FileUtils.readStringFromStream(ins);
			}
			logger.info("responseCode:" + conn.getResponseCode() + ",url:" + url);
			if (responseCode == 500) {
				ins = conn.getErrorStream();
				logger.error(FileUtils.readStringFromStream(ins));
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public String getParamsStr(Object object) {
		if (object instanceof Map) {
			Map<String, Object> map = (Map) object;
			if (map.isEmpty()) {
				return null;
			}
			return joinMap(map);
		} else {
			return joinBean(object);
		}
	}
	
	public String joinMap(Map<String, Object> map) {
		StringBuilder sBuilder = new StringBuilder();
		for (Object key : map.keySet()) {
			sBuilder.append(key).append("=").append(map.get(key)).append("&");
		}
		if (sBuilder.length() > 0) {
			sBuilder.deleteCharAt(sBuilder.length() - 1);
		}
		return sBuilder.toString();
	}

	public String joinBean(Object bean) {
		StringBuilder sBuilder = new StringBuilder();
		PropertyDescriptor[] descriptors = ReflectUtils.getBeanProperties(bean.getClass());
		try {
			for (PropertyDescriptor descriptor : descriptors) {
				Object val = descriptor.getReadMethod().invoke(bean);
				if (val != null) {
					sBuilder.append(descriptor.getName()).append("=").append(val).append("&");
				}
			}
			if (sBuilder.length() > 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
			}
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		return sBuilder.toString();
	}
	
	/**
	 * 设置 http  header 属性
	 */
	public HttpClient addHeader(String property, String value) {
		headers.put(property, value);
		return this;
	}
}
