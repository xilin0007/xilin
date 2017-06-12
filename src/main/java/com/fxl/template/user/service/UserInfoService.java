package com.fxl.template.user.service;

import java.util.List;

import com.fxl.exception.ServiceException;
import com.fxl.frame.base.BaseService;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.vo.VOUserPageList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface UserInfoService extends BaseService<UserInfo> {
	/**
	 * 返回分页用户列表信息
	 * @version 1.0
	 * @createTime 2017-3-8,下午5:14:05
	 * @createAuthor fangxilin
	 * @param qvo
	 * @return
	 */
	public VOUserPageList findByPage(int page, int limit);
	
	/**
	 * 更新回滚测试
	 * @version 1.0
	 * @createTime 2017-3-14,下午3:42:03
	 * @createAuthor fangxilin
	 * @param uId1
	 * @param uId2
	 * @return
	 */
	public boolean updateFindRollBack (int uId1, int uId2) throws ServiceException;
	
	/**
	 * 
	 * @version 1.0
	 * @createTime 2017-3-16,下午5:25:32
	 * @createAuthor fangxilin
	 * @param nickName
	 * @param size
	 * @return
	 */
	public List<UserInfo> findByNickName(String nickName, Integer size) throws ServiceException;
	
	/**
	 * 搜索分页查询用户列表页面
	 * @version 1.0
	 * @createTime 2017-4-1,下午4:58:44
	 * @createAuthor fangxilin
	 * @param page
	 * @param nickName
	 * @return
	 */
	public PageInfo<UserInfo> listPageUser(Page<UserInfo> page, String nickName) throws ServiceException;
	
	/**
	 * 返回分页列表
	 * @createTime 2017-6-6,下午6:03:43
	 * @createAuthor fangxilin
	 * @param pageNum
	 * @param pageSize
	 * @param nickName
	 * @return
	 */
	public List<UserInfo> listPageUser(int pageNum, int pageSize, String nickName);
}
