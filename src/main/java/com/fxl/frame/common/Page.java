package com.fxl.frame.common;

import java.io.Serializable;

public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize = 10;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
}
