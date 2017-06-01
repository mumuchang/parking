package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;

public class Record extends BmobObject{

	private String username;
    private String license;
    private String parkingName;
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
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	
}
