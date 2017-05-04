package com.parking.reserve;

import com.baidu.mapapi.model.inner.GeoPoint;

import cn.bmob.v3.BmobObject;

public class ParkinglotInfo extends BmobObject{
    int totalNum;
	int price;
    GeoPoint point;
	String parkinglot_name;
	String description;
	int currentLeftNum;
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
    public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public GeoPoint getPoint() {
		return point;
	}
	public void setPoint(GeoPoint point) {
		this.point = point;
	}
	public String getParkinglot_name() {
		return parkinglot_name;
	}
	public void setParkinglot_name(String parkinglot_name) {
		this.parkinglot_name = parkinglot_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCurrentLeftNum() {
		return currentLeftNum;
	}
	public void setCurrentLeftNum(int currentLeftNum) {
		this.currentLeftNum = currentLeftNum;
	}
	
	
}
