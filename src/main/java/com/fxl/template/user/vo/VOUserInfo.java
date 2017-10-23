package com.fxl.template.user.vo;


public class VOUserInfo {
	private Integer id;
	
	private String nick_name;
	
	private String user_img;

    private Integer pregnant_stage;

    private Integer pregnant_week;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}

	public Integer getPregnant_stage() {
		return pregnant_stage;
	}

	public void setPregnant_stage(Integer pregnant_stage) {
		this.pregnant_stage = pregnant_stage;
	}

	public Integer getPregnant_week() {
		return pregnant_week;
	}

	public void setPregnant_week(Integer pregnant_week) {
		this.pregnant_week = pregnant_week;
	}
    
}
