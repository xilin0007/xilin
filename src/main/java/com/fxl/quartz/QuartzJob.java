package com.fxl.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.fxl.log.LogTool;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.mapper.UserInfoMapper;
import com.fxl.template.user.service.UserInfoService;

public class QuartzJob {
	
	protected LogTool log = LogTool.getInstance(new Object[]{this.getClass()});
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 定时器测试
	 * @version 1.0
	 * @createTime 2017-3-9,下午5:10:19
	 * @createAuthor fangxilin
	 */
	public void quartzJobTest() {
		try {
			UserInfo user = userInfoService.findById(8091);
			log.info("用户信息："+user.getNickName());
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			log.info(sdf.format(DateTime.now().toDate())+"*********B任务每5秒执行一次进入测试");
		} catch (Exception e) {
			log.error("异常");
		}
	}
}
