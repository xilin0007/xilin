package com.fxl.template.user.vo;

import java.util.List;

import com.fxl.template.user.entity.UserInfo;
import com.github.pagehelper.PageInfo;

public class VOUserPageList {
	private List<UserInfo> userList;
	private PageInfo<UserInfo> pageInfo;
	public List<UserInfo> getUserList() {
		return userList;
	}
	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}
	public PageInfo<UserInfo> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<UserInfo> pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
