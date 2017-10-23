package com.fxl.frame.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 计算公式，公共方法工具类
 * ClassName: FunctionUtils 
 * @Description: TODO
 * @author fangxilin
 * @date 2016-5-14
 */

public class FunctionUtils {
	/**
     * bmi  小于 18.5
     */
    public static final int BMI18P5 = 0;
    /**
     * bmi 18.5 - 24.9
     */
    public static final int BMI18P5_24P9 = 1;
    /**
     * bmi 25 - 29.9
     */
    public static final int BMI25_29P9 = 2;
    /**
     * bmi 大于 30
     */
    public static final int BMI30 = 3;
    public static final float[] WeightSamplesBeforeThriteen = {1, 3};
    private static final int WeekThriteen = 13;
    private static final int TOTALWEEK = 40;
    //不同BMI区间对应的增量
    public static final float[][] WeightSamples = {
            {12.5f, 18},
            {11.5f, 16},
            {7, 11.5f},
            {5, 9}
    };
    
    
	/**计算bmi*/
    public static double getBMI(double height,double weight){ //静态函数
    	double bmi = weight/((height/100)*(height/100));
    	bmi = setDecimal(bmi, 1);
        return bmi;
    }
    
    /**
	 * 计算孕周
	 * @param test_date 当前时间  
	 * @param expected_date 预产期
	 * @return pregnant_week[0] 孕周   pregnant_week[1] 零几天  pregnant_week[2] 总天数
	 */
	public static int[] calPregnantWeek(Date test_date, Date expected_date){
    	if(test_date == null || expected_date == null){
    		return null;
    	}
    	if(test_date.after(expected_date)){
	    	return new int[] {40,0,280};
	    }
    	int[] pregnant_week = new int[3];
    	long pregnant = expected_date.getTime()/1000 - 3600*24*280;
    	Long interval_t = (test_date.getTime()/1000 - pregnant)/(3600*24);
    	int interval = interval_t.intValue();
    	if(interval >= 280){
    		pregnant_week[0] = 40;
    		pregnant_week[1] = 0;
    		pregnant_week[2] = 280;
    	}else if(interval<0){
			pregnant_week[0] = 0;
			pregnant_week[1] = 0;
			pregnant_week[2] = 0;
		}else{
    		pregnant_week[0] = interval/7;
	    	pregnant_week[1] = interval%7;
	    	pregnant_week[2] = interval;
    	}
    	return pregnant_week;
    }
	
	/**
	 * 根据末次月经时间计算预产期
	 * @param lastPeriod 末次月经时间
	 * @return 
	 */
	public static Date getPregancyDay(String lastPeriod){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sdf.parse(lastPeriod);
			Calendar Calendarborn = Calendar.getInstance();
			Calendarborn.setTime(d1);
			Calendarborn.add(Calendar.DAY_OF_MONTH, 279);
			return Calendarborn.getTime();
			/*int bornyear = Calendarborn.get(Calendar.YEAR);
			int bornmonth = 1 + Calendarborn.get(Calendar.MONTH);
			int bornday = Calendarborn.get(Calendar.DAY_OF_MONTH);
			String borndayString = (String.valueOf(bornyear) + "-" + String.valueOf(bornmonth) + "-" + String.valueOf(bornday));
			return TimeUtils.convertDate(borndayString, "yyyy-MM-dd");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据预产期计算末次月经
	 * @version 1.0
	 * @createAuthor fangxilin
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param expectedDate 预产期
	 * @return
	 */
	public static Date getLastPeriodByExp(Date expectedDate){
		if(expectedDate == null){
			return null;
		}
		Calendar todayStart = Calendar.getInstance();
		todayStart.setTime(expectedDate);
		todayStart.add(Calendar.DATE, -279);
		return todayStart.getTime();
	}
	
	/**
	 * 自定义的除法运算
	 * @param 
	 * @return 
	 */
	public static double division(Double num1, Double num2){
		if(num2 == 0 || num2 == null){
			return 0;
		}
		return num1/num2;
	}
	
	/**
	 * 保留n位小数位
	 * @param num 要保留的小数位的数
	 * @param n 要保留n位有效数字
	 * @return 
	 */
	public static double setDecimal(Double num, int n){
		BigDecimal b = new BigDecimal(num);
		double result = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();	
		return result;
	}
	
	/**
	 * 得到安全体重的范围,根据 孕前bmi，孕前体重，怀孕天数
	 * @param $init_bmi 孕前bmi
	 * @param $init_weight 孕前体重
	 * @param $day 怀孕天数
	 * @return $safe_weight[0]最小增长值,$safe_weight[1]最大增长值
	 */
	public static double[] getSafeWeight(double init_bmi, double init_weight, double totalday) {
		double[] $safe_weight = new double[2];
        float maxValue, minValue;
        
        Double $init_bmi = init_bmi;
        Double $init_weight = init_weight;
        Double $day = totalday;

        int allDay = $day.intValue();
        int week = allDay / 7 ;
        int day = allDay%7;
        
        int BmiState = getWeightState($init_bmi.doubleValue());
        float minThriteen = WeightSamplesBeforeThriteen[0];
        float maxThriteen = WeightSamplesBeforeThriteen[1];
        //这里是按公斤来算，
        if (week <= WeekThriteen) {
            minValue = allDay * minThriteen * 1.0f   / (WeekThriteen * 7 + 6 ) + $init_weight.floatValue();
            maxValue = allDay * maxThriteen * 1.0f / (WeekThriteen * 7 + 6 ) + $init_weight.floatValue();

        } else {
            //此处运用数学知识
            float minAllWeek = WeightSamples[BmiState][0];
            float maxAllWeek = WeightSamples[BmiState][1];

            int dayLast = TOTALWEEK * 7  - (WeekThriteen *7 + 6);

            int week_days = week * 7 - (WeekThriteen *7 +6) + day;

            minValue = week_days * (minAllWeek - minThriteen) / dayLast  + minThriteen + $init_weight.floatValue();
            maxValue = week_days * (maxAllWeek - maxThriteen) /dayLast  + maxThriteen +$init_weight.floatValue();
        }
        
        $safe_weight[0] = minValue;
        $safe_weight[1] = maxValue;
        return $safe_weight;
	}
	 
   //bmi对应的增量区间
    private static int getWeightState(Double bmi){
    	if (bmi < 18.5) {
            return BMI18P5;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI18P5_24P9;
        } else if (bmi >= 25 && bmi < 30) {
            return BMI25_29P9;
        } else {
            return BMI30;
        }
	}
    public static float round(double num,int status){
    	java.text.DecimalFormat df=new java.text.DecimalFormat("#.#");
    	if(status ==2){
    		df=new java.text.DecimalFormat("#.##"); 
    	}
    	float sourceF = new Float(df.format(num));
    	return sourceF;
    }
    
    /**
	 * 获得推荐摄入的卡洛里
	 * @param $height 身高
	 * @param $bmi 孕前bmi
	 * @param $type 计算公式 0：公式一   1：公式二
	 * @return $safe_weight[0]最小增长值,$safe_weight[1]最大增长值
	 */
    public static float getSuggestIntake(float $height,float $bmi,int $type){
    	float $recommendedIntake =1700;
    	
        if($type==0){
            float $x=($bmi<=18.5)?200:((18.5 < $bmi && $bmi <= 24.9)?100:0);
            $recommendedIntake = ($height - 105.00f) * 30 +$x ;
        }
        else if($type==1){
            if($bmi<=18.5){
                $recommendedIntake=($height-105.00f)*35;
            }
            else if($bmi>18.5&&$bmi<=24.9){
                $recommendedIntake=($height-105.00f)*30;
            }
            else{ //其实就是大于25
                $recommendedIntake=($height-105.00f)*25;
            }
        }
        return $recommendedIntake;
    }
    
    /**
	 * 将手机号转换成123*****456的形式
	 * @param mobile 手机号码
	 */
    public static String getPrivateMobile(String mobile){
    	if(StringUtils.isEmpty(mobile)){
    		return null;
    	}
    	mobile = mobile.substring(0, 3)+"*****"+mobile.substring(8, 11);
    	return mobile;
    }
    
    /**
	 * 根据怀孕天数计算孕期阶段
	 * @param day 怀孕天数
	 * @return stage 孕期阶段
	 */
    public static String calPregnantStage(Integer day){
    	String stage = "孕早期";
    	if(day == null){
    		return stage;
    	}
    	if(day>=0 && day<=90){ //0到12周6天为孕早期
			stage = "孕早期";
		}else if(day>=91 && day<=195){ //13周0天到27周6天为孕中期
			stage = "孕中期";
		}else if(day>=196){
			stage = "孕晚期";
		}
    	return stage;
    }
}
