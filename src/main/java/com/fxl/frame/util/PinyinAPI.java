package com.fxl.frame.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinAPI {
	
	/** 分词正则表达式 */  
    public final static String regEx  = "[^aoeiuv]?h?[iuv]?(ai|ei|ao|ou|er|ang?|eng?|ong|a|o|e|i|u|ng|n)?";
	
	/**
	 * 字符串字符简写字母，eg：你好，转换后为：NH
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		if (isNull(str)) {
			return "";
		}
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			//获取拼音
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
	
	/**
	 * 获取字符串第一个字符（汉字为第一个字）的首字母（大写）
	 * @param str
	 * @return
	 */
	public static String getFirstHead(String str) {
		if(isNull(str)){
			return "";
		}
		String subStr = str.substring(0, 1);
		String ss = PinyinAPI.getPinYinHeadChar(subStr);
		return ss;
	}
	
	/***
	 * 获取汉字字符串的全拼
	 * @param str
	 * @param type 1：hao3，2：hao，3：hăo
	 * @return
	 */
	public static String getAllPinYin(String str, int type) {
		HanyuPinyinToneType toneType = null;
		switch (type) {
		case 2:
			toneType = HanyuPinyinToneType.WITHOUT_TONE;
			break;
		case 3:
			toneType = HanyuPinyinToneType.WITH_TONE_MARK;
			break;
		default:
			toneType = HanyuPinyinToneType.WITH_TONE_NUMBER;
			break;
		}
		HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();
		format.setToneType(toneType);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		//format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
		StringBuilder pinyinArr = new StringBuilder();
		try	{
			for (int i = 0; i < str.length(); i++) {
				String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(str.charAt(i), format);
				if (pinyin != null && pinyin.length > 0) {
					//只取一个发音，如果是多音字，仅取第一个发音
					pinyinArr.append(pinyin[0] + " ");
				} else {
					//如果是非汉字就保持原样
					pinyinArr.append(str.charAt(i));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinyinArr.toString().trim();
	}
	
	/**
	 * 拼音用'分割开
	 * @param input eg:koudingboke
	 * @return eg:kou'ding'bo'ke
	 */
	public static String split(String input) {
        int tag = 0;  
        StringBuffer sb = new StringBuffer();  
        String formatted = "";  
        List<String> tokenResult = new ArrayList<String>();  
        for (int i = input.length(); i > 0; i = i - tag) {  
            Pattern pat = Pattern.compile(regEx);  
            Matcher matcher = pat.matcher(input);  
            //boolean rs = matcher.find();  
            sb.append(matcher.group());  
            sb.append("\'");  
            tag = matcher.end() - matcher.start();  
            tokenResult.add(input.substring(0, 1));  
            input = input.substring(tag);  
        }  
        if (sb.length() > 0) {  
            formatted = sb.toString().substring(0, sb.toString().length() - 1);  
        }  
        return formatted;  
    }

	/**
	 * 是否为空
	 * @param strData
	 * @return
	 */
	public static boolean isNull(Object strData) {
		if (strData == null || String.valueOf(strData).trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉空格
	 * @param value
	 * @return
	 */
	public static String string2AllTrim(String value) {
		if (isNull(value)) {
			return "";
		}
		return value.trim().replace(" ", "");
	}

	public static void main(String[] args) {
		String str = "你好";
		/*String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(str.charAt(1));
		System.out.print(pinyinArray[0].charAt(0));*/
		
		/*String ss = getPinYinHeadChar(str);
		System.out.println(ss);*/
		
		/*String ss = getAllPinYin("荆溪白石出，Hello 天寒红叶稀。Android 山路元无雨，What's up? 空翠湿人衣。", 3);
		System.out.println(ss);*/
		
		String ss = "tds";
		System.out.println(split(ss));
	}
}
