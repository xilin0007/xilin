package com.fxl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LogTool {
	private Logger logger = LoggerFactory.getLogger(LogTool.class);
	//protected Logger logger = Logger.getLogger(this.getClass());

	public static LogTool getInstance(Object[] objects) {
		LogTool log = new LogTool();
		if ((objects != null) && (objects.length > 0)
				&& (objects[0] instanceof Class)) {
			log.logger = LoggerFactory.getLogger((Class) objects[0]);
			//log.logger = Logger.getLogger((Class) objects[0]);
		}
		return log;
	}

	public void debug(Object message) {
		this.logger.debug("☆☆☆【" + message + "】☆☆☆");
	}

	public void error(Object message, Object[] objects) {
		if ((objects != null) && (objects.length > 0)
				&& (objects[0] instanceof Throwable))
			this.logger
					.error("◆◆◆【" + message + "】◆◆◆", (Throwable) objects[0]);
		else
			this.logger.error("◆◆◆【" + message + "】◆◆◆");
	}

	public void error(Object message) {
		if (message instanceof Throwable)
			this.logger.error("接口调用报错 ◆◆◆[" + message + "]◆◆◆",
					(Throwable) message);
		else
			this.logger.error("◆◆◆【" + message + "】◆◆◆");
	}

	public void error(Exception message) {
		this.logger.error("◆◆◆【" + message.getMessage() + "】◆◆◆");
	}

	public void info(Object message) {
		this.logger.info("★★★【" + message + "】★★★");
	}

	public void warn(Object message) {
		this.logger.warn("※※※【" + message + "】※※※");
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}
}