package com.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 正则匹配
 * @Description TODO
 * @author fangxilin
 * @date 2018年10月26日
 * @Copyright: 深圳市宁远科技股份有限公司版权所有(C)2018
 */
public class RegexTest {

    public static void main(String[] args) {
        String content = "ftp://192.168.0.17/2018-10-23/1005420181023245812008.pdf";
        String regex = "^(ftp://)+";
        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);  
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + ":" + matcher.group(i));
            }
        } else {
            System.out.println("NO MATCH");
        } 
        
        
        
        
        
        String s = "\\\\192.168.0.17\\US$\\Report\\44740189374090427.pdf";
        System.out.println(s);
        System.out.println(StringEscapeUtils.escapeJava(s));
    }

}
