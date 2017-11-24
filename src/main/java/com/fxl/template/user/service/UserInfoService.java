package com.fxl.template.user.service;

import java.util.List;

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
	 * 设置rollback-for属性可新增抛出该异常时也回滚，但不设置也会有默认的抛出运行时异常时回滚
	 * 1.不捕获异常(一般处理方式)，2.捕获异常，并抛出RuntimeException异常，3.捕获异常，并继续抛出原捕获的异常，
	 * 4.捕获异常，但不处理，不抛出，5.捕获异常，并抛出新new的异常
	 * 1，2，3会回滚	4，5不会回滚
	 * 注意：如果在事务方法中捕获异常并进行处理，一定要继续抛出异常并在Spring事务管理中进行rollbak-for配置
	 * @createTime 2017-3-14,下午3:42:03
	 * @createAuthor fangxilin
	 * @param userId
	 * @param hospitalId
	 * @return
	 */
	public boolean updateFindRollBack (int userId, int hospitalId);
	
	/**
	 * 
	 * @version 1.0
	 * @createTime 2017-3-16,下午5:25:32
	 * @createAuthor fangxilin
	 * @param nickName
	 * @param size
	 * @return
	 */
	public List<UserInfo> findByNickName(String nickName, Integer size);
	
	/**
	 * 搜索分页查询用户列表页面
	 * @version 1.0
	 * @createTime 2017-4-1,下午4:58:44
	 * @createAuthor fangxilin
	 * @param page
	 * @param nickName
	 * @return
	 */
	public PageInfo<UserInfo> listPageUser(Page<UserInfo> page, String nickName);
	
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
