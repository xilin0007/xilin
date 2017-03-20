package com.fxl.frame.base;

import java.util.List;

import com.fxl.exception.ServiceException;

public interface BaseService<T extends BaseEntity> {
	
	public T findById(Integer id) throws ServiceException;
	
	public boolean insert(T paramT) throws ServiceException;

	public boolean insertBatch(List<T> paramList) throws ServiceException;

	public boolean delete(Integer id) throws ServiceException;

	public boolean update(T paramT) throws ServiceException;

	public boolean updateBatch(List<T> paramList) throws ServiceException;
}