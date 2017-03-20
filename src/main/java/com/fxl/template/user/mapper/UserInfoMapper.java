package com.fxl.template.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxl.frame.base.BaseMapper;
import com.fxl.frame.common.QVOCondition;
import com.fxl.template.user.entity.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
	
	/**
	 * 分页查询用户列表
	 * @version 1.0
	 * @createTime 2017-3-8,下午5:14:05
	 * @createAuthor fangxilin
	 * @param qvo
	 * @return
	 */
	public List<UserInfo> findByPage(QVOCondition qvo);
	
	/**
	 * 
	 * @version 1.0
	 * @createTime 2017-3-16,下午5:24:43
	 * @createAuthor fangxilin
	 * @param nickName
	 * @param size
	 * @return
	 */
	public List<UserInfo> findByNickName(@Param("nickName") String nickName, @Param("size") Integer size) throws Exception;
}