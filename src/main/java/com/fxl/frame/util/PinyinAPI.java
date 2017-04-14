/*package com.fxl.frame.util;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinAPI {
	*//**
	 * 字符串字符简写字母，eg：你好，转换后为：NH
	 * 
	 * @param str
	 * @return
	 *//*
	public static String getPinYinHeadChar(String str) {
		if (isNull(str)) {
			return "";
		}
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// ��ȡ���ֵ�����ĸ
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}

		convert = string2AllTrim(convert);
		return convert.toUpperCase();
	}

	
	 * �ж��ַ��Ƿ�Ϊ��
	 

	public static boolean isNull(Object strData) {
		if (strData == null || String.valueOf(strData).trim().equals("")) {
			return true;
		}
		return false;
	}

	*//**
	 * ȥ���ַ������пո�
	 * 
	 * @param value
	 * @return
	 *//*
	public static String string2AllTrim(String value) {
		if (isNull(value)) {
			return "";
		}
		return value.trim().replace(" ", "");
	}
	*//**
	 * 获取字符串第一个字符（汉字为第一个字）的首字母（大写）
	 * @param str
	 * @return
	 *//*
	public static String getFirstHead(String str){
		if(isNull(str)){
			return "";
		}
		String subStr = str.substring(0, 1);
		String ss = PinyinAPI.getPinYinHeadChar(subStr);
		return ss;
	}

	public static void main(String[] args) {
		String str = "adc";
		String ss = getFirstHead(str);
		System.out.print(ss);
	}
}
*/