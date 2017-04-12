package com.fxl.frame.base;

import java.util.List;

public interface BaseMapper<T extends BaseEntity> {
	
	public T findById(Integer id);
	
	public Integer insert(T paramT) throws Exception;

	public Integer insertBatch(List<T> paramList) throws Exception;

	public Integer delete(Integer id) throws Exception;

	public Integer update(T paramT) throws Exception;
	
	public Integer updateBatch(List<T> paramList) throws Exception;

}