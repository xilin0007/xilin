package com.fxl.template.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
import com.fxl.frame.util.constant.Consts;
import com.fxl.frame.util.file.FileUtils;
import com.fxl.frame.util.file.UploadUtils;

/**
 * 文件上传
 */
@RequestMapping("/file")
@Controller
public class FileController extends BaseController {
	
	private final static Logger logger = Logger.getLogger(FileController.class);
	
	/**
	 * 执行上传
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public ReturnMsg upload(HttpServletRequest request) {
		try {
			// 上传文件
			ReturnMsg ret = doUpload(request);
			return ret;
			//return new ReturnMsg(ReturnMsg.SUCCESS, "上传成功！", ret.getData());
		} catch (Exception e) {
			logger.error("upload", e);
			return new ReturnMsg(ReturnMsg.FAIL, "上传失败！");
		}
	}
	
	/**
	 * 文件上传
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public ReturnMsg doUpload(HttpServletRequest request) throws IllegalStateException, IOException {
		ReturnMsg ret = new ReturnMsg(0, "文件上传失败");
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						//重命名上传后的文件名
						String fileName = getUUIDString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
						// 定义上传路径
						String basePath = request.getSession().getServletContext().getRealPath("upload") + "/";
						checkFilePath(basePath);
						String path = basePath + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
						ret = uploadToFileServer(localFile);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * 生产32为UUID，作为文件名
	 * 
	 * @return
	 */
	private String getUUIDString() {
		return UUID.randomUUID().toString().replace("-", "");
	}


	/**
	 * 检查路径的有效性，若不存在则创建
	 * @param path
	 */
	private void checkFilePath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 将文件上传到文件服务器上去
	 * @param type
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public ReturnMsg uploadToFileServer(File file) throws IOException {
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("file", file);
		HashMap<String, String> params = new HashMap<String, String>();
		ReturnMsg ret = UploadUtils.sendMultyPartRequest(Consts.BASE_URL + Consts.UPLOAD_PATH, params, files);
		//时长
		//如果文件后缀为mp3，则获取mp3播放时长
		if(file.getName().indexOf("mp3")>0) {
			//获取mp3时长
			String duration = "#" + FileUtils.getDurationLength(file, 1) + "#";
			ret.setData(ret.getData() + duration);
		}
		file.delete();
		return ret;
	}
}
