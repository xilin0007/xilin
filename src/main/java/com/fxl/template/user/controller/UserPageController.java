package com.fxl.template.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.service.UserInfoService;

@Controller
@RequestMapping("/userPage")
public class UserPageController extends BaseController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping("/demo")
	public String demo() {
		return "user/demo";
	}
	
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
}
