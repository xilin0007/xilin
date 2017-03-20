package com.fxl.exception;

import java.io.Serializable;

public class ExcpMessage implements Serializable {
	private static final long serialVersionUID = -4265730752736473644L;
	private Integer code;
	private String msg;

	public ExcpMessage(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ExcpMessage [code=" + this.code + ", msg=" + this.msg + "]";
	}
}