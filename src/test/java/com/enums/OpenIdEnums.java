package com.enums;

public enum OpenIdEnums {
	yisheng(1, "ys_", "医生"),
	yonghu(2, "yh_", "用户"),
	yiyuan(3, "yy_", "医院"),
	yunying(4, "yx_", "运营");
	
	private int index;
	private String value;
	private String commnet;
	private OpenIdEnums(int index, String value, String commnet) {
		this.index = index;
		this.value = value;
		this.commnet = commnet;
	}
	
	public static String getOpenIdByIndex(int index) {
		OpenIdEnums[] openIdEnums = OpenIdEnums.values();
		for (OpenIdEnums open : openIdEnums) {
			if (open.index == index) {
				return open.value;
			}
		}
		return null;
	}
}
