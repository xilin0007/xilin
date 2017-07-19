package com.fxl.frame.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fxl.exception.ServiceException;
import com.fxl.log.LogTool;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	protected LogTool log = LogTool.getInstance(new Object[]{this.getClass()});
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract BaseMapper<T> getDao();
	
	@Override
	public T findById(Integer id) {
		return getDao().findById(id);
	}
	
	@Override
	public boolean insert(T paramT) throws ServiceException {
		try {
			return (getDao().insert(paramT)>0);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean insertBatch(List<T> list) throws ServiceException {
		try {
			if ((list != null) && (list.size() > 0)) {
				return (getDao().insertBatch(list)>0);
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return false;
	}

	@Override
	public boolean delete(Integer id) throws ServiceException {
		try {
			return (getDao().delete(id)>0);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean update(T Po) throws ServiceException {
		try {
			return (getDao().update(Po)>0);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean updateBatch(List<T> paramList) throws ServiceException {
		try {
			return (getDao().updateBatch(paramList)>0);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}