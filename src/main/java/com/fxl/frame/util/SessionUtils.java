package com.fxl.frame.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * session
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-14
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class SessionUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);
	
	/**
	 * 获取session--baseUrl供http调用
	 * @createTime 2017-6-14,上午10:51:03
	 * @createAuthor fangxilin
	 * @return
	 */
	public static String getHttpBaseUrl() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Object obj = request.getSession().getAttribute("baseUrl");
		String baseUrl = "";
		if (obj != null && obj != "") {
			baseUrl = (String) request.getSession().getAttribute("baseUrl");
		}
		return baseUrl;
	}
}
