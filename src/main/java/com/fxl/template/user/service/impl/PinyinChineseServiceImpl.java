package com.fxl.template.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxl.frame.base.BaseMapper;
import com.fxl.frame.base.BaseServiceImpl;
import com.fxl.template.user.entity.PinyinChinese;
import com.fxl.template.user.mapper.PinyinChineseMapper;
import com.fxl.template.user.service.PinyinChineseService;

@Service
public class PinyinChineseServiceImpl extends BaseServiceImpl<PinyinChinese> implements PinyinChineseService {
	
	@Autowired
	private PinyinChineseMapper pinyinChineseMapper;
	
	@Override
	protected BaseMapper<PinyinChinese> getDao() {
		return pinyinChineseMapper;
	}

	@Override
	public List<PinyinChinese> listByPinyin(String query, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
