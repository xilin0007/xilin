package com.fxl.frame.common;
/**
 * @desc dubbo服务调用，数据返回接口处理
 */
import java.io.Serializable;

public class BaseDataResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 数据响应状态码 **/
	private Integer code;
	/** 出错信息 **/
	private String error;
	/** 数据集 **/
	private Object data;
	 
	public BaseDataResult(Integer code, String error){
		this.code = code;
		this.error = error;
	}
	
	public BaseDataResult(Integer code, String error, Object data){
		this.code = code;
		this.error = error;
		this.data = data;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
