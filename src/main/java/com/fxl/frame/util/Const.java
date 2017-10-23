package com.fxl.frame.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * final常量
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class Const {
	/** 版本号 v+日期+版本 **/
	public final static String VERSION = "V1704013.1.0.0";
	
	/** yyyy-MM-dd时间格式 **/
	public static final String YYYYMMDD = "yyyy-MM-dd";
	
	/** yyyy-MM-dd HH:mm:ss时间格式 **/
	public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 这种共有的静态final数组域，虽然是final，但是数组（对象的引用）是可变的
	 * 通过Const.MEALS_NAME[0] = "";可改变值，会存在安全漏洞
	 */
	public static final String[] MEALS_NAME = {"早餐","早点","午餐","午点","晚餐","晚点"};
	
	/**
	 * 通过生成unmodifiableList不可修改list的方式解决
	 */
	private static final String[] PRIVATE_MEALS = {"早餐","早点","午餐","午点","晚餐","晚点"};
	public static final List<String> MEALS = Collections.unmodifiableList(Arrays.asList(PRIVATE_MEALS));
	
}
