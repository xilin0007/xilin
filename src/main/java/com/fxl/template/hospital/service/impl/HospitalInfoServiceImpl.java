package com.fxl.template.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fxl.frame.base.BaseMapper;
import com.fxl.frame.base.BaseServiceImpl;
import com.fxl.template.hospital.entity.HospitalInfo;
import com.fxl.template.hospital.mapper.HospitalInfoMapper;
import com.fxl.template.hospital.service.HospitalInfoService;

@Service
public class HospitalInfoServiceImpl extends BaseServiceImpl<HospitalInfo> implements HospitalInfoService {
	
	@Autowired
	private HospitalInfoMapper hospitalInfoMapper;
	
	@Override
	protected BaseMapper<HospitalInfo> getDao() {
		return hospitalInfoMapper;
	}

}
