package com.fxl.template.file.controller;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
import com.fxl.frame.util.FileToolUtils;
import com.fxl.template.file.service.FileBaseDataService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/fastdfs")
@Api(value="/fastdfs", description="文件上传的接口")
public class FastdfsController extends BaseController {
	
	@Autowired
	private FileBaseDataService fileBaseDataService;
	
	/**
	 * 单文件上传--转化成文件形式上传
	 * @createTime 2017-7-19,下午6:18:27
	 * @createAuthor fangxilin
	 * @param file
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/V1.0/uploadFile")
	@ApiOperation(value = "单文件上传", httpMethod = "POST", response = ReturnMsg.class, notes = "单文件上传", position = 1)
	public ReturnMsg uploadFileV1(@RequestParam(value = "file", required = false) MultipartFile file){
		try {
			String basePath = getRequest().getSession().getServletContext().getRealPath("upload") + "/";
			FileToolUtils.mkdir(basePath);
			String path = basePath + file.getOriginalFilename();
			logger.info("-----------要上传的文件路径：" + path);
			File localFile = new File(path);
			file.transferTo(localFile);
			ReturnMsg ret = fileBaseDataService.uploadFile(localFile);
			return ret;
		} catch (Exception e) {
			log.error("单上传文件异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "单文件上传异常", new ArrayList<>());
		}
	}
	
	/**
	 * 单文件上传--获取流形式上传
	 * @createTime 2017-7-19,下午6:18:27
	 * @createAuthor fangxilin
	 * @param file
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
	@ApiOperation(value = "单文件上传", httpMethod = "POST", response = ReturnMsg.class, notes = "单文件上传", position = 1)
	public ReturnMsg uploadFile(@RequestParam(value = "file", required = false) MultipartFile file){
		try {
			logger.info("-----------要上传的文件名：" + file.getOriginalFilename());
			return fileBaseDataService.uploadFile(file);
		} catch (Exception e) {
			log.error("单上传文件异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "单文件上传异常", new ArrayList<>());
		}
	}
}
