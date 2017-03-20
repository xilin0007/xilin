package com.fxl.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fxl.log.LogTool;
import com.fxl.template.user.entity.UserInfo;
import com.fxl.template.user.mapper.UserInfoMapper;

@Component
public class QuartzJobBak {
	
	protected LogTool logTool = new LogTool();
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	/**
	 * 定时器测试
	 * @version 1.0
	 * @createTime 2017-3-9,下午5:10:19
	 * @createAuthor fangxilin
	 */
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次      
	public void quartzJobTest() {
		try {
			UserInfo user = userInfoMapper.findById(8091);
			logTool.info("用户信息："+user.getNickName());
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logTool.info(sdf.format(DateTime.now().toDate())+"*********B任务每5秒执行一次进入测试");
		} catch (Exception e) {
			logTool.error("异常");
		}
	}
}
