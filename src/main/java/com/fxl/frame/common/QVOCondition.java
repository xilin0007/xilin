package com.fxl.frame.common;

import java.io.Serializable;

/**
 * 公共条件查询类
 * @Description TODO
 * @author fangxilin
 * @date 2016-12-6
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public class QVOCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Integer userId;
	public boolean isUserPage = true;//是否需要分页、默认需要
	public int page;//页数
	public int limit;//每页大小
	public int startIndex;//开始的记录
	public int endIndex;//结束的记录
	public Integer type;//eg：（报告类型1.产检报告 2.B超报告 3.乳腺报告 4.查询所有）
	public Integer doctorId;
	public String nickName;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getStartIndex() {
		if(startIndex<=1){
			startIndex = 0;
		}else if(limit<=0){ //默认每页显示5条
			startIndex = (page-1)*5;
		}else{
			startIndex = (page-1)*limit;
		}
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		if(limit<=0){ //默认每页显示5条
			endIndex = startIndex+5;
		}else{
			endIndex = startIndex+limit;
		}
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public boolean isUserPage() {
		return isUserPage;
	}
	public void setUserPage(boolean isUserPage) {
		this.isUserPage = isUserPage;
	}
	public Integer getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
