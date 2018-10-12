package com.fxl.frame.util.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 获取客户端（请求方）的ip地址
 * @Description TODO
 * @author fangxilin
 */
public class WebRequestUtils {
	
	/**
	 * 获取客户端（请求方）的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		return ip;

		/*
		 * String ip = request.getHeader( "X-Real-IP" ); if (ip == null ||
		 * ip.length() == 0 || " unknown " .equalsIgnoreCase(ip)) { ip =
		 * request.getHeader( " Proxy-Client-IP " ); } if (ip == null ||
		 * ip.length() == 0 || " unknown " .equalsIgnoreCase(ip)) { ip =
		 * request.getHeader( " WL-Proxy-Client-IP " ); } if (ip == null ||
		 * ip.length() == 0 || " unknown " .equalsIgnoreCase(ip)) { ip =
		 * request.getRemoteAddr(); } return ip;
		 */
	}

	/** 获取头部传过来的device_information里的字段 **/
	public static String getDeviceInformation(String field,
			ObjectMapper mapper, HttpServletRequest request) {
		try {
			String deviceInformation = request.getHeader("device_information");
			if (StringUtils.isBlank(deviceInformation)) {
				return "";
			}
			return mapper.readTree(deviceInformation).path(field).asText();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
