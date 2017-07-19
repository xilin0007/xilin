package com.fxl.frame.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fxl.frame.util.Const;
import com.fxl.log.LogTool;

@Controller
@RequestMapping("/base")
public class BaseController {
	
	protected LogTool log = LogTool.getInstance(new Object[]{this.getClass()});
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public HttpServletRequest getRequest(){
		return this.request;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}
	@ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) throws IOException{
        this.request = request;
        this.response = response;
        this.request.setAttribute("v", Const.VERSION);
    }
}
