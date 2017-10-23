package com.fxl.template.user.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fxl.frame.base.BaseController;
import com.fxl.frame.common.ReturnMsg;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.service.UserInfoService;
import com.fxl.template.user.vo.VOUserPageList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户模块")
public class UserController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/findUsersById")
	@ApiOperation(value = "获取用户信息", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户信息", position = 1)
	public ReturnMsg findUsersById(@ApiParam(value = "用户id") @RequestParam int userId) {
		try {
			UserInfo user = userInfoService.findById(userId);
			Object data = (user != null) ? user : new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户信息成功", data);
		} catch (Exception e) {
			log.error("获取用户信息异常");
			e.printStackTrace();
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户信息异常", new ArrayList<>());
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/findUsersByPage")
	@ApiOperation(value = "获取用户列表", httpMethod = "POST", response = ReturnMsg.class, notes = "获取用户列表", position = 2)
	public ReturnMsg findUsersByPage(@ApiParam(value = "页数") @RequestParam int page,
			@ApiParam(value = "每页大小") @RequestParam int limit) {
		try {
			VOUserPageList vo = userInfoService.findByPage(page, limit);
			Object data = (vo != null) ? vo : new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "获取用户列表成功", data);
		} catch (Exception e) {
			log.error("获取用户列表异常");
			return new ReturnMsg(ReturnMsg.FAIL, "获取用户列表异常", new ArrayList<>());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/testRallBack")
	@ApiOperation(value = "测试事务回滚", httpMethod = "GET", response = ReturnMsg.class, notes = "测试事务回滚", position = 3)
	public ReturnMsg testRallBack(@ApiParam(value = "用户ID1") @RequestParam int userId1,
			@ApiParam(value = "用户ID2") @RequestParam int userId2) {
		try {
			boolean ret = userInfoService.updateFindRollBack(userId1, userId2);
			if(ret == false){
				return new ReturnMsg(ReturnMsg.FAIL, "事务回滚功能", new ArrayList<>());
			}
			Object data = new ArrayList<>();
			return new ReturnMsg(ReturnMsg.SUCCESS, "测试事务回滚成功", data);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("测试事务回滚异常");
			return new ReturnMsg(ReturnMsg.FAIL, "测试事务回滚异常", new ArrayList<>());
		}
	}
	
}
