package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;

public class License extends BmobObject{

	private String username;
    private String license;
      public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}

      
}
