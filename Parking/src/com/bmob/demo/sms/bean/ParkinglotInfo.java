package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;
import com.baidu.mapapi.model.inner.GeoPoint;

/**
 * Created by lenovo on 2017/5/1.
 */
public class ParkinglotInfo extends BmobObject{
    private int totalNum;
    private Number latitude;
    private Number longitude;
    private String parkinglot_name;
    private String description;
    private int currentLeftNum;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
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

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }
}
