package com.fxl.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 过滤器测试
 * @author fangxilin
 * @date 2018年2月27日
 */
public class FilterTest implements Filter {

	/**
	 * init方法，tomcat启动时执行
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----过滤器初始化----");
		//得到过滤器的名字
		String filterName = filterConfig.getFilterName();
		//得到在web.xml文件中配置的初始化参数
		String initParam1 = filterConfig.getInitParameter("name");//fangxl
		String initParam2 = filterConfig.getInitParameter("like");//java
		//返回过滤器的所有初始化参数的名字的枚举集合
		Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
		System.out.println(filterName);
		System.out.println(initParam1);
		System.out.println(initParam2);
		while (initParameterNames.hasMoreElements()) {
			String paramName = (String) initParameterNames.nextElement();//name,like
			System.out.println(paramName);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("FilterTest执行前！！！");
		chain.doFilter(request, response); // 让目标资源执行，放行
		System.out.println("FilterTest执行后！！！");
	}

	/**
	 * tomcat销毁时执行
	 */
	@Override
	public void destroy() {
		System.out.println("----过滤器销毁----");
	}

}
