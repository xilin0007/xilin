package com.fxl.template.user.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
import com.fxl.frame.util.DownloadFile;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.service.UserInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/userPage")
public class UserPageController extends BaseController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/demo")
	public String demo() {
		return "user/demo";
	}
	
	/**
	 * 搜索自动补全
	 * @version 1.0
	 * @createTime 2017-4-1,下午4:43:28
	 * @createAuthor fangxilin
	 * @param nickName
	 * @param size
	 * @return
	 */
	@RequestMapping("/findByNickName")
	@ResponseBody
	public ReturnMsg findByNickName(@RequestParam(value = "nickName") String nickName,
			@RequestParam("size") int size){
		try {
			List<UserInfo> returnList = userInfoService.findByNickName(nickName, size);
			Object data = (returnList != null) ? returnList : new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "搜索成功", data);
		} catch (Exception e) {
			return new ReturnMsg(ReturnMsg.FAIL, "搜索异常", new ArrayList<>());
		}
	}
	
	@RequestMapping("/listPageUser")
	public String listPageUser(ModelMap model, Page<UserInfo> page, String nickName) {
		try {
			page.setPageSize(10);
			PageInfo<UserInfo> pageInfo = userInfoService.listPageUser(page, nickName);
			model.put("nickName", nickName);
			model.put("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/listPageUser";
	}
	
	/**
	 * 测试传List参数
	 * @createTime 2017-4-28,下午4:35:03
	 * @createAuthor fangxilin
	 * @param idList
	 * @return
	 */
	@RequestMapping("/testIdList")
	@ResponseBody
	public ReturnMsg testIdList(@RequestParam(value = "idList[]") List<Integer> idList){
		try {
			log.info(idList.toString());
			return new ReturnMsg(ReturnMsg.SUCCESS, "搜索成功");
		} catch (Exception e) {
			return new ReturnMsg(ReturnMsg.FAIL, "搜索异常", new ArrayList<>());
		}
	}
	
	@RequestMapping("/downloadNet")
	public void downloadNet(HttpServletResponse response, 
			@RequestParam(value = "urlString") String urlString) {  
        // 下载网络文件  
		String fileName = "二维码.jpg";
		//fileName = new String(fileName.getBytes(),"iso-8859-1");
        String filePath = DownloadFile.download(urlString, fileName);
        OutputStream outStream = null;
        InputStream inStream = null;
        try {
	    	inStream = new BufferedInputStream(new FileInputStream(filePath));
		    byte[] buffer = new byte[inStream.available()];
		    inStream.read(buffer);
		    response.reset();
		    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		    response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1"));
		    outStream = new BufferedOutputStream(response.getOutputStream());
		    response.setContentType("application/octet-stream");
		    outStream.write(buffer);// 输出文件
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inStream, outStream);
			 /** 最后将临时文件删除 **/
        	File file = new File(filePath);
        	FileUtils.deleteQuietly(file);
		}
    }
	
}
