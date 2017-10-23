package com.fxl.frame.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 生成拼音检索sql
 * @Description TODO
 * @author fangxilin
 * @date 2017-6-20
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class GeneratePinyinSql {
	
	private static final String FILE_DIR = "F:/temp/";
	
	/**
	 * 生成不带有字数字段（word_num）的sql
	 * @throws IOException
	 */
	public static void generate() throws IOException {
		File filetxt = new File(FILE_DIR + "搜狗标准词库.txt");
		List<String> lineList = new ArrayList<String>();
		StringBuilder sqlsb = new StringBuilder();
		sqlsb.append("INSERT INTO pinyin_chinese (pinyin, py_header, chinese) VALUES \n");
		lineList = FileUtils.readLines(filetxt, "utf-8");
		for (String line : lineList) {
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			String[] lineArr = line.split(" ");//[ni'hao,你好,10]
			String pinyin = lineArr[0];//拼音全称
			String[] pinyinArr = pinyin.split("'");
			StringBuilder sb = new  StringBuilder();
			for (String py : pinyinArr) {
				sb.append(py.charAt(0) + "'");
			}
			String pyHeader = sb.toString().substring(0, sb.length() - 1);//拼音首字母
			String chinese = lineArr[1];//汉字
			//sql.append("('" + pinyin + "', '" + pyHeader + "', '" + chinese + "')");
			sqlsb.append(String.format("(\"%s\", \"%s\", \"%s\"),\n", pinyin, pyHeader, chinese));
		}
		String sql = sqlsb.substring(0, sqlsb.length() - 2) + ";";
		File filesql = new File(FILE_DIR + "搜狗标准词库.sql");
		//写入文件
		FileUtils.writeStringToFile(filesql, sql, "utf-8");
		System.out.println("生成SQL文件成功！");
	}
	
	/**
	 * 生成带有字数字段（word_num）的sql
	 * @throws IOException
	 */
	public static void generateV2() throws IOException {
		File filetxt = new File(FILE_DIR + "搜狗标准词库.txt");
		List<String> lineList = new ArrayList<String>();
		StringBuilder sqlsb = new StringBuilder();
		sqlsb.append("INSERT INTO pinyin_chinese (pinyin, py_header, chinese, word_num) VALUES \n");
		lineList = FileUtils.readLines(filetxt, "utf-8");
		for (String line : lineList) {
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			String[] lineArr = line.split(" ");//[ni'hao,你好,10]
			String pinyin = lineArr[0];//拼音全称
			String[] pinyinArr = pinyin.split("'");
			int wordNum = pinyinArr.length;//字数
			StringBuilder sb = new  StringBuilder();
			for (String py : pinyinArr) {
				sb.append(py.charAt(0) + "'");
			}
			String pyHeader = sb.toString().substring(0, sb.length() - 1);//拼音首字母
			String chinese = lineArr[1];//汉字
			//sql.append("('" + pinyin + "', '" + pyHeader + "', '" + chinese + "')");
			sqlsb.append(String.format("(\"%s\", \"%s\", \"%s\", %s),\n", pinyin, pyHeader, chinese, wordNum));
		}
		String sql = sqlsb.substring(0, sqlsb.length() - 2) + ";";
		File filesql = new File(FILE_DIR + "搜狗标准词库V2.sql");
		//写入文件
		FileUtils.writeStringToFile(filesql, sql, "utf-8");
		System.out.println("生成SQL文件成功！");
	}
	
	public static void main(String[] args) {
		try {
			//generate();
			generateV2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
