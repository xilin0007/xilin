package com.fxl.frame.base;

import java.util.List;

/**
 * @Description TODO
 * @author fangxilin
 * @date 2017-4-13
 * @Copyright: Copyright (c) 2016 Shenzhen Angelsound Technology Co., Ltd. Inc. 
 *             All rights reserved.
 */
public interface BaseMapper<T extends BaseEntity> {
	
	T findById(Integer id);
	
	List<T> listByIds(List<Integer> ids);
	
	List<T> listAll();
	
	Integer insert(T paramT);

	Integer insertBatch(List<T> paramList);

	Integer delete(Integer id);
	
	Integer deleteBatch(List<Integer> ids);
	
	Integer update(T paramT);
	
	Integer updateBatch(List<T> paramList);

}