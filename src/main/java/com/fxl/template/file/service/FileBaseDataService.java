package com.fxl.template.file.service;

import java.io.File;

import com.fxl.frame.common.ReturnMsg;

public interface FileBaseDataService {
	/**
	 * 上传单个文件
	 * @createTime 2017-7-17,下午4:58:37
	 * @createAuthor fangxilin
	 * @param file
	 * @return
	 */
	public ReturnMsg uploadFile(File file);
}
