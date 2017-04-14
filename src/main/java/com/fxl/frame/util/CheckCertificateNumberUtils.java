package com.fxl.frame.util;

public class CheckCertificateNumberUtils {

	
	/**
	 * 判断证件类型：0：身份证；1：护照；2：军官照；3：港澳居民通行证
	 * @param number	证件号码
	 * @param type		证件类型
	 * @return
	 */
	public static boolean checkCertificateNumber(String number, int type){
		if(type == 0){			//身份证号
			CheckIdCardNumber check = new CheckIdCardNumber(number);
			if(check.isCorrect() != 0){
				return false;
			}
		}else if(type == 1){		//护照号码
			String passport_regex="^(P\\d{7}|G\\d{8}|S\\d{7,8}|D\\d+|1[4,5]\\d{7}|E\\d{7,8})$";
			if(!number.matches(passport_regex)){
				return false;
			}
		}else if(type == 2){		//军官证号码
			String officer_regex ="^[\u2E80-\u9FFF]\\d{8}";
			if(!number.matches(officer_regex)){
				return false;
			}
		}else if(type == 3){		//港澳居民通行证
			String ga_regex = "^(H\\d{10}|M\\d{10})";
			if(!number.matches(ga_regex)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 检查身份证号是否不合法
	 * @param idCard
	 * @return
	 */
	public static boolean checkIdCardNumber(String idCard){
		
		CheckIdCardNumber check = new CheckIdCardNumber(idCard);
		if(check.isCorrect() != 0){
			return false;
		}
		
		return true;
	}
}
