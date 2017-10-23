package com.fxl.exception;

public class SystemExcpMessages {
	public static final ExcpMessage SYS_SUCCESS = new ExcpMessage(
			Integer.valueOf(0), "success");
	public static final ExcpMessage SYS_ERROR_404 = new ExcpMessage(
			Integer.valueOf(404), "请求不存在404");
	public static final ExcpMessage SYS_ERROR_400 = new ExcpMessage(
			Integer.valueOf(400), "请检查接口需要的参数");
	public static final ExcpMessage SYS_ERROR_405 = new ExcpMessage(
			Integer.valueOf(405), "请求方式错误");

	public static final ExcpMessage SYS_SERVER_THROWS_EXCEPTION = new ExcpMessage(
			Integer.valueOf(500), "服务器出现繁忙，请稍后再请求.");

	public static final ExcpMessage SYS_REQUEST_PARAMETER_ERROR = new ExcpMessage(
			Integer.valueOf(100), "请求参数错误。");

	public static final ExcpMessage SYS_SERVER_TIMEOUT = new ExcpMessage(
			Integer.valueOf(101), "响应超时,服务器繁忙.");

	public static final ExcpMessage SYS_USER_NOT_LOGIN = new ExcpMessage(
			Integer.valueOf(102), "请登录.");

	public static final ExcpMessage SYS_SESSION_OVERDUE = new ExcpMessage(
			Integer.valueOf(103), "请重新登录");

	public static final ExcpMessage SYS_NO_FUNCTION = new ExcpMessage(
			Integer.valueOf(104), "没有实现的功能！");

	public static ExcpMessage getExcpMessage(Integer code, String msg) {
		return new ExcpMessage(code, msg);
	}
}