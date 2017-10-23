
package com.enums;


public enum AppIdEnums {
    
    TS("101", "天使"), HY("102", "弘扬"), JH("103", "建海");
    
    private String value;
    private String comment;
	private AppIdEnums(String value, String comment) {
		this.value = value;
		this.comment = comment;
	}
}
