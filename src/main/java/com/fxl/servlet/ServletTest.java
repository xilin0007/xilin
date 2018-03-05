package com.fxl.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		//处理get请求
		System.out.println("处理get请求");
		ServletConfig config = this.getServletConfig();
		//得到servlet名称
		System.out.println("servlet Name：" + config.getServletName());
		Enumeration<String> initParameterNames = config.getInitParameterNames();
		while (initParameterNames.hasMoreElements()) {
			//得到初始化参数名
			String name = (String) initParameterNames.nextElement();
			System.out.println("servle init-param-name：" + name);
		}
		//得到初始化参数值
		System.out.println("servle init-param-value：" + config.getInitParameter("param1"));
		
		/**************servletContext-----start**************/
		//每个项目都有唯一一个servletContext，共享数据，每个servlet都可以访问它
		ServletContext servletContext = config.getServletContext();
		System.out.println("context-param-value：" + servletContext.getInitParameter("myContextParam"));
		Enumeration<String> initParameterNames2 = servletContext.getInitParameterNames();
		while (initParameterNames2.hasMoreElements()) {
			//得到servletContext初始化参数名
			String name = (String) initParameterNames2.nextElement();
			System.out.println("context-param-name：" + name);
		}
		//得到servletContext初始化参数值
		System.out.println("context-param-value：" + servletContext.getInitParameter("myContextParam"));
		
		//获取web项目下指定资源的路径
		String realPath = servletContext.getRealPath("/WEB-INF/web.xml");
		System.out.println("web.xml realPath：" + realPath);
		//获取指定资源的流
		InputStream in = servletContext.getResourceAsStream("/WEB-INF/web.xml");
		/**************servletContext-----end**************/
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		//处理post请求
		System.out.println("处理post请求");
	}
	
}
