package com.fxl.template.user.service;

import java.util.List;

import com.fxl.frame.base.BaseService;
import com.fxl.template.user.entity.PinyinChinese;

public interface PinyinChineseService extends BaseService<PinyinChinese> {
	
	/**
	 * 根据拼音搜索
	 * @createTime 2017-6-21,下午3:31:08
	 * @createAuthor fangxilin
	 * @param query
	 * @param size
	 * @return
	 */
	public List<PinyinChinese> listByPinyin(String query, int size);
}
