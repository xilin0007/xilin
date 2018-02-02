package com.fxl.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 * 日志应包括：
 * 	时间，操作账号，操作者----通过登录账号获得
 * 	动作 （eg：添加，删除），对象 （eg：孕妇），对象内容（某某人）-----通过注解设置
 * 	eg：管理员查询（动作operate）了孕妇（对象module）某某人（对象内容content）
 * @author fangxilin
 * @date 2018年2月2日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecordInfo {
	
	/**
	 * 动作
	 */
	String operate();
	/**
	 * 对象
	 */
	String module();
	/**
	 * 对象内容
	 */
	String content();
}
