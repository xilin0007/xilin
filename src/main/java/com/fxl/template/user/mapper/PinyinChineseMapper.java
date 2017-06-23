package com.fxl.template.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fxl.frame.base.BaseMapper;
import com.fxl.template.user.entity.PinyinChinese;

public interface PinyinChineseMapper extends BaseMapper<PinyinChinese> {
	/**
	 * 根据拼音搜索
	 * @createTime 2017-6-21,下午3:31:08
	 * @createAuthor fangxilin
	 * @param query
	 * @param size
	 * @return
	 */
	public List<PinyinChinese> listByPinyin(@Param("query") String query, @Param("size") int size);
}