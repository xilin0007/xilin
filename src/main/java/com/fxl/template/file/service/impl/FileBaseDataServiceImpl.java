package com.fxl.template.file.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fxl.frame.common.ReturnMsg;
import com.fxl.frame.util.file.FastDFSClient;
import com.fxl.template.file.service.FileBaseDataService;

@Service
public class FileBaseDataServiceImpl implements FileBaseDataService {
	
	protected Logger logger = Logger.getLogger(FileBaseDataServiceImpl.class);
	
	@Override
	public ReturnMsg uploadFile(File file) {
		try {
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			String fileName = file.getName();
			String fileUrl = FastDFSClient.uploadFile(file, fileName);
			paramMap.put("fileUrl", fileUrl);
			return new ReturnMsg(ReturnMsg.SUCCESS, "上传文件成功", paramMap);
		} catch (Exception e) {
			logger.info("上传图片文件 Msg = "+e.getMessage(), e);
		}
		return new ReturnMsg(ReturnMsg.FAIL, "上传文件失败", new ArrayList<Object>());
	}

}
