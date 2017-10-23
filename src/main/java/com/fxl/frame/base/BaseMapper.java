package com.fxl.frame.base;

import java.util.List;

/**
 * 对数据库进行增删改时需要抛出Exception
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface BaseMapper<T extends BaseEntity> {
	
	public T findById(Integer id);
	
	public Integer insert(T paramT) throws Exception;

	public Integer insertBatch(List<T> paramList) throws Exception;

	public Integer delete(Integer id) throws Exception;

	public Integer update(T paramT) throws Exception;
	
	public Integer updateBatch(List<T> paramList) throws Exception;

}