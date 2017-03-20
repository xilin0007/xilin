package com.fxl.template.user.service;

import java.util.List;

import com.fxl.exception.ServiceException;
import com.fxl.frame.base.BaseService;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.vo.VOUserPageList;

public interface UserInfoService extends BaseService<UserInfo> {
	/**
	 * 分页查询用户列表
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
}
