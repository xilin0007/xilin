package com.junit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.fxl.template.user.mapper.UserInfoMapper;
import com.fxl.template.user.service.UserInfoService;
import com.fxl.template.user.vo.VOUserPageList;

public class ServiceTest extends JUnitDaoBase {
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Test
	@Rollback(false)
	public void examService() {
		try {
			boolean ret = userInfoService.updateFindRollBack(8091, 6028);
			//logger.info("====================>"+user.toString());
			System.out.println("====================>"+ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void examEhcache() {
		try {
			VOUserPageList vo = userInfoService.findByPage(1, 10);
			//logger.info("====================>"+user.toString());
			System.out.println("====================>"+vo.getPageInfo().getTotal());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
