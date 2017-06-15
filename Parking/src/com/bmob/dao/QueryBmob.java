package com.bmob.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.SimpleAdapter;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.bmob.demo.sms.bean.License;
import com.bmob.demo.sms.bean.Record;
import com.bmob.demo.sms.bean.User;
import com.parking.R;
import com.parking.service.LicenseActivity;


public class QueryBmob {

	List<String > list;
	List<HashMap<String, String>> list2,list3;
	public List<String> queryLicense(){
		
	    list = new ArrayList<String>();
		BmobQuery<License> query = new BmobQuery<License>();
		User user = BmobUser.getCurrentUser(User.class);
		query.addWhereEqualTo("username", user.getUsername());
		query.setLimit(10);
		// 执行查询方法
		query.findObjects(new FindListener<License>() {
			@Override
			public void done(List<License> arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
				if (arg1 == null) {
					for (License lic : arg0) {
						list.add(lic.getLicense());
					}
				} else {

				}
			}
		});
		return list;
	}
	public List<HashMap<String, String>> queryLicense2(){
		list3=new ArrayList<HashMap<String,String>>();
		BmobQuery<License> query = new BmobQuery<License>();
		User user = BmobUser.getCurrentUser(User.class);
		query.addWhereEqualTo("username", user.getUsername());
		query.setLimit(10);
		// 执行查询方法
		query.findObjects(new FindListener<License>() {
			@Override
			public void done(List<License> arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
				if (arg1 == null) {
					for(License lic:arg0){
						  HashMap<String ,String> map=new HashMap<String,String>();
						  map.put("license", lic.getLicense());
					      list3.add(map);
					}
				} else {

				}
			}
		});		
		return list3;
	}
	public List<HashMap<String, String>> queryRecord(){
		
		list2=new ArrayList<HashMap<String,String>>();
		BmobQuery<Record> query = new BmobQuery<Record>();
		User user = BmobUser.getCurrentUser(User.class);
		query.addWhereEqualTo("username", user.getUsername());
		query.setLimit(10);
		// 执行查询方法
		query.findObjects(new FindListener<Record>() {
			@Override
			public void done(List<Record> arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
				if (arg1 == null) {
					for(Record rr:arg0){
						  HashMap<String ,String> map=new HashMap<String,String>();
						  map.put("license", rr.getLicense());
						  map.put("name", rr.getParkingName());
						  map.put("time", rr.getCreatedAt());
					      list2.add(map);
					}
				} else {
                 
				}
			}
		});
		return list2;
	}
}
