package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;
import com.baidu.mapapi.model.inner.GeoPoint;

/**
 * Created by lenovo on 2017/5/1.
 */
public class ParkinglotInfo extends BmobObject{
    private Number totalNum;
    private Number latitude;
    private Number longitude;
    private String parkinglot_name;
    private String description;
    private Number currentLeftNum;
    private Number price;

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public Number getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Number totalNum) {
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

    public Number getCurrentLeftNum() {
        return currentLeftNum;
    }

    public void setCurrentLeftNum(Number currentLeftNum) {
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
