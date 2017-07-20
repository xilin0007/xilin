package com.fxl.template.file.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			String fileUrl = FastDFSClient.uploadFile(file, fileName, null);
			paramMap.put("fileUrl", fileUrl);
			return new ReturnMsg(ReturnMsg.SUCCESS, "上传文件成功", paramMap);
		} catch (Exception e) {
			logger.info("上传文件 Msg = " + e.getMessage(), e);
		} finally {
			boolean ret = file.delete();
			logger.info("---------删除临时文件结果：" + ret);
		}
		return new ReturnMsg(ReturnMsg.FAIL, "上传文件失败", new ArrayList<Object>());
	}

	@Override
	public ReturnMsg uploadFile(MultipartFile file) {
		try {
			String fileUrl = FastDFSClient.uploadFile(file.getInputStream(), file.getOriginalFilename(), null);
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fileUrl", fileUrl);
			return new ReturnMsg(ReturnMsg.SUCCESS, "上传文件成功", paramMap);
		} catch (Exception e) {
			logger.info("上传文件 Msg = " + e.getMessage(), e);
		}
		return new ReturnMsg(ReturnMsg.FAIL, "上传文件失败", new ArrayList<Object>());
	}

}
