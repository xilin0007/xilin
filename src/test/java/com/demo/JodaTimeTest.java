package com.demo;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Joda-Time在Java SE 8之前成为Java的实际标准日期和时间库
 * 从Java SE 8起，用户被要求迁移到java.time,取代了Soda-Time这个项目
 * @Description TODO
 * @author fangxilin
 * @date 2018年6月27日
 */
public class JodaTimeTest {

    public static void main(String[] args) {
        
        /**
         * DateTime构造方法
         */
        System.out.println("DateTime构造方法");
        DateTime dt = new DateTime(new Date());
        System.out.println(dt);
        DateTime dt1 = new DateTime(2018, 1, 30, 13, 30, 50);
        System.out.println(dt1);
        DateTime dt3 = new DateTime(System.currentTimeMillis());
        System.out.println(dt3);
        Date date = dt3.toDate();
        System.out.println(date);
        
        /**
         * LocalDate构造方法
         */
        System.out.println("LocalDate构造方法");
        LocalDate localDate = new LocalDate();
        System.out.println(localDate);//2017-06-26
        
        /**
         * LocalTime构造方法
         */
        System.out.println("LocalTime构造方法");
        LocalTime localtime = new LocalTime();
        System.out.println(localtime);//16:07:23.727
        
        /**
         * with开头的方法使用
         * 用来设置DateTime实例到某个时间
         * 返回了设置后的一个副本对象
         */
        System.out.println("with开头的方法");
        DateTime dateTime2000Year = new DateTime(2000,2,29,0,0,0);
        System.out.println(dateTime2000Year); // out: 2000-02-29T00:00:00.000+08:00
        DateTime dateTime1997Year = dateTime2000Year.withYear(1997); 
        System.out.println(dateTime1997Year); // out: 1997-02-28T00:00:00.000+08:00
        
        /**
         * plus/minus开头的方法，返回在DateTime实例上增加或减少一段时间后的实例
         */
        System.out.println("plus/minus开头的方法");
        DateTime now = new DateTime();
        System.out.println(now); // out: 2016-02-26T16:27:58.818+08:00
        DateTime tomorrow = now.plusDays(1);
        System.out.println(tomorrow); // out: 2016-02-27T16:27:58.818+08:00
        DateTime lastMonth = now.minusMonths(1);
        System.out.println(lastMonth); // out: 2016-01-26T16:27:58.818+08:00
        
        /**
         * Property类中方法的使用
         */
        System.out.println("Property类中方法的使用");
        System.out.println(now.monthOfYear().getAsText());// February
        System.out.println(now.monthOfYear().getAsText(Locale.KOREAN));// 2월
        System.out.println(now.dayOfWeek().getAsShortText());// Fri
        System.out.println(now.dayOfWeek().getAsShortText(Locale.CHINESE));// 星期五
        
        /**
         * 格式化时间
         */
        System.out.println("格式化时间");
        String nowstr = now.toString("yyyy-MM-dd HH:mm:ss");
        System.out.println(nowstr);
    }

}
