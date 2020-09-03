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



        /**
         * ()表示分组，
         * + 一次或多次匹配前面的字符或子表达式
         * . 匹配除"\r\n"之外的任何单个字符
         * * 零次或多次匹配前面的字符或子表达式
         * ? 零次或一次匹配前面的字符或子表达式
         * *? 如果 ? 是限定符 * 或 或 ? 或 {} 后面的第一个字符,那么表示非贪婪模式(尽可能少的匹配字符),而不是默认的贪婪模式
         * \ 将下一字符标记为特殊字符、文本、反向引用或八进制转义符
         * 在匹配 . 或 { 或 [ 或 ( 或 ? 或 $ 或 ^ 或 * 这些特殊字符时,需要在前面加上 \\
         * 比如匹配 . 时,Java 中要写为 \\.,但对于正则表达式来说就是 \.
         * 在匹配 \ 时,Java 中要写为 \\\\,但对于正则表达式来说就是 \\
         * $ 匹配输入字符串结尾的位置
         */

//        String content = "ftp://192.168.0.17/2018-10-23/1005420181023245812008.pdf";
//        String regex = "^(ftp://)+";

//        String content = "https://yzs.yygr.cn/ywz/#/interrogation-fail?name={name}&age={age}";
//        //非贪婪模式
//        String regex = "\\{(.*?)\\}";
        //tanlanMoS();


//        String content = "https://wxis.91160.com/wxis/act/order/physicalExamOrderPage.do?unit_id=140&code=aadfsd232aa";
//        String regex = "&code=(.*?)&";

        String content = "https://wxis.91160.com/wxis/act/order/physicalExamOrderPage.do?unit_id=140&code=aadfsd232aa";
        String regex = "&code=(.*?)$"; //匹配某字符串到结尾后的内容

        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(content);
        if (mat.find()) {
            System.out.println(mat.group(0));
            System.out.println(mat.group(1));
            //&code=aadfsd232aa& => &
            content = content.replace(mat.group(0), "&");
            System.out.println(content);
        }
        while(mat.find()) {
            for (int i = 0; i <= mat.groupCount(); i++) {
                System.out.println(i + ":" + mat.group(i));
            }
        }



        
        /*String s = "\\\\192.168.0.17\\US$\\Report\\44740189374090427.pdf";
        System.out.println(s);
        System.out.println(StringEscapeUtils.escapeJava(s));*/
    }

    /**
     * 贪婪模式与非贪婪模式
     */
    public static void tanlanMoS() {
        String content = "<div>文章标题</div><div>发布时间</div>";
        // 贪婪模式
        //String regex = "<div>(?<title>.+)</div>";
        // 非贪婪模式
        String regex = "<div>(?<title>.+?)</div>";
        Pattern pattern = Pattern.compile(regex);
        Matcher mat = pattern.matcher(content);
        while(mat.find()) {
            System.out.println(mat.group("title"));
        }
    }

}
