package com.fxl.template.file.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.fxl.frame.common.ReturnMsg;

public interface FileBaseDataService {
	/**
	 * 上传单个文件--文件形式
	 * @createTime 2017-7-17,下午4:58:37
	 * @createAuthor fangxilin
	 * @param file
	 * @return
	 */
	public ReturnMsg uploadFile(File file);
	
	/**
	 * 上传单个文件--流形式
	 * @createTime 2017-7-17,下午4:58:37
	 * @createAuthor fangxilin
	 * @param file
	 * @return
	 */
	public ReturnMsg uploadFile(MultipartFile file);
}
