package com.fxl.template.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
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
	public ReturnMsg findByNickName(@RequestParam(value = "idList[]") List<Integer> idList){
		try {
			log.info(idList.toString());
			return new ReturnMsg(ReturnMsg.SUCCESS, "搜索成功");
		} catch (Exception e) {
			return new ReturnMsg(ReturnMsg.FAIL, "搜索异常", new ArrayList<>());
		}
	}
}
