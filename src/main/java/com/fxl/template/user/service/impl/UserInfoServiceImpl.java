package com.fxl.template.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxl.frame.base.BaseMapper;
import com.fxl.frame.base.BaseServiceImpl;
import com.fxl.template.hospital.entity.HospitalInfo;
import com.fxl.template.hospital.mapper.HospitalInfoMapper;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.mapper.UserInfoMapper;
import com.fxl.template.user.service.UserInfoService;
import com.fxl.template.user.vo.VOUserPageList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	
	@Override
	protected BaseMapper<UserInfo> getDao() {
		return userInfoMapper;
	}
	
	@Override
	public VOUserPageList findByPage(int page, int limit) {
		log.info("测试info日志打印");
		String nickName = "天使医生11";
		PageHelper.startPage(page, limit);
		List<UserInfo> list = userInfoMapper.findByPage(nickName);
		PageInfo<UserInfo> pages = new PageInfo<UserInfo>(list);
		VOUserPageList vo = new VOUserPageList();
		vo.setUserList(list);
		vo.setPageInfo(pages);
		return vo;
	}

	@Override
	public boolean updateFindRollBack(int userId, int hospitalId) {
		UserInfo user = new UserInfo();
		user.setNickName("rollBackUser");
		user.setId(userId);
		HospitalInfo hosp = new HospitalInfo();
		hosp.setId(hospitalId);
		hosp.setName("rollBackHosp");
		userInfoMapper.update(user);
		hospitalInfoMapper.update(hosp);//错误代码，SQL语句错误，模拟事务
		//int a = 3 / 0;//模拟异常
		return true;
	}

	@Override
	public List<UserInfo> findByNickName(String nickName, Integer size) {
		return userInfoMapper.findByNickName(nickName, size);
	}

	@Override
	public PageInfo<UserInfo> listPageUser(Page<?> page, String nickName) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserInfo> list = userInfoMapper.findByPage(nickName);
		PageInfo<UserInfo> pages = new PageInfo<UserInfo>(list);
		return pages;
	}

	@Override
	public List<UserInfo> listPageUser(int pageNum, int pageSize, String nickName) {
		PageHelper.startPage(pageNum, pageSize);
		List<UserInfo> list = userInfoMapper.findByPage(nickName);
		return list;
	}
	
}
