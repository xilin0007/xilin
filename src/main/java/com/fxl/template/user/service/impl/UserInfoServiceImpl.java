package com.fxl.template.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxl.exception.ServiceException;
import com.fxl.frame.base.BaseMapper;
import com.fxl.frame.base.BaseServiceImpl;
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
	public boolean updateFindRollBack(int uId1, int uId2) throws ServiceException {
		UserInfo u1 = new UserInfo();
		u1.setNickName("rollBackName");
		u1.setId(uId1);
		try {
			Integer ret = userInfoMapper.update(u1);
			int a = 3 / 0;//模拟异常
			return (ret > 0);
		} catch (Exception e) {
			log.error("异常，将进行回滚");
			throw new ServiceException();
		}
	}

	@Override
	public List<UserInfo> findByNickName(String nickName, Integer size) throws ServiceException{
		return userInfoMapper.findByNickName(nickName, size);
	}

	@Override
	public PageInfo<UserInfo> listPageUser(Page<UserInfo> page, String nickName) throws ServiceException {
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
