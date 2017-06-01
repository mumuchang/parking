package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;

public class Suggestion extends BmobObject{

	private String username;
	private String suggest;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
}
