package com.fxl.frame.util.data;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fxl.frame.util.constant.Const;


/**
 * 时间工具类
 * @Description TODO
 * @author fangxilin
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class TimeUtils {
	
	/**
	 * 快捷string转Date
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param data
	 * @return
	 */
	public static Date convertToDate(String data) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.YYYYMMDD);// 可以方便地修改日期格式
		Date ret;
		try {
			ret = dateFormat.parse(data);
			return ret;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * string格式化Date
	 * @param data
	 * @param format
	 * @return
	 */
	public static Date stringFormatToDate(String data, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date langDate;
		try {
			langDate = sdf.parse(data);
			return langDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String格式化String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String stringFormatString(String date, String format) {
		String time = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			time = sdf.format(sdf.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * Date格式化String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormatToString(Date date, String format) {
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String转为Timestamp
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String date, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		format.setLenient(false);
		try {
			Timestamp ts = new Timestamp(format.parse(date).getTime());
			return ts;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String timestampToString(Long timestamp, String formatStr) {
		if (timestamp == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			return sdf.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前时间往后多少天
	 * @param day
	 * @param date
	 * @return
	 */
	public static Date getDateByDaysLate(int day, Date date) {
		Calendar todayStart = Calendar.getInstance();
		if (date != null) {
			todayStart.setTime(date);
		}
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		todayStart.add(Calendar.DATE, day);
		return todayStart.getTime();
	}
	
	/**************************************************************************/
	
	/**
	 * 判断是否是指定的日期格式
	 * @param dateStr
	 * @param fomart
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String fomart) {
		boolean convertSuccess = true;
		if(StringUtils.isEmpty(dateStr)){
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat(fomart);
		try {
			format.setLenient(false);
			format.parse(dateStr);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 获得起始时间和结束时间内的所有时间集合
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);
        // 测试结束日期是否在该日期之前  
        while (endDate.after(cal.getTime())) {
        	lDate.add(cal.getTime());
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        int ret = beginDate.compareTo(endDate);
        if(ret < 1){
        	lDate.add(endDate);// 把结束时间加入集合  
        }
        return lDate;  
    }
	
	/**
	 * 获取某年某月某日的日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDateFormatByNum(Integer year,Integer month,Integer day) {
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(cal.getTime());
        return time;
	}
	
	/**
	 * 两个时间之间相差距离多少天 
	 * @param str1 时间参数 1
	 * @param str2 时间参数 2
	 * @return 相差天数 
	 */
    public static int getDistanceDayss(String str1, String str2) {  
        DateFormat df = new SimpleDateFormat(Const.YYYYMMDD);  
        Date one, two;
        int days = 0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            diff = time1 - time2;  
            days = (int) (diff / (1000 * 60 * 60 * 24));  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return days;  
    }
    
    /**
	 * 判断date1-date2是否在限定的多少秒内
	 * @param date1
	 * @param date2
	 * @param limitTime 限定的多少秒
	 * @return
	 */
	public static boolean isInLimitTime(Date date1, Date date2, int limitTime) {
		if (date1 == null || date2 == null) {
			return false;
		}
		long t1 = date1.getTime();
		long t2 = date2.getTime();
		// 因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
		int hours = (int) ((t1 - t2) / 3600000);
		int minutes = (int) (((t1 - t2) / 1000 - hours * 3600) / 60);
		int second = (int) ((t1 - t2) / 1000 - hours * 3600 - minutes * 60);
		int sec = hours * 3600 + minutes * 60 + second;
		if (sec > limitTime) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取本周周x的时间，周一为第一天，周日为最后一天
	 * @createTime 2018年1月15日,上午10:11:37
	 * @createAuthor fangxilin
	 * @param week 周一~周日：0-6
	 * @return
	 */
	public static Date getDateByWeek(int week) {
		Calendar cal = Calendar.getInstance();
		int nowWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (nowWeek == Calendar.SUNDAY) {
			nowWeek = 6;
		} else {
			nowWeek -= 2;
		}
		return getDateByDaysLate(week - nowWeek, cal.getTime());
	}
}
	



