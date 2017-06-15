package com.parking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.bmob.dao.QueryBmob;
import com.bmob.demo.sms.bean.Record;
import com.bmob.demo.sms.bean.User;
import com.parking.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class RecordActivity extends Activity {

	ImageView back;
	SimpleAdapter listAdapter;
	ListView mylist;
	List<HashMap<String, String>> list;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		back = (ImageView) findViewById(R.id.iv_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		mylist = (ListView) findViewById(R.id.mylist);
		list = new ArrayList<HashMap<String, String>>();
		init();
	}

	private void init() {
		// TODO 自动生成的方法存根
       // QueryBmob qb=new QueryBmob();
       // list=qb.queryRecord();
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
					      list.add(map);
					  	  listAdapter = new SimpleAdapter(RecordActivity.this, list,
								R.layout.recorditem,
								new String[] { "time", "license", "name" }, new int[] {
										R.id.time, R.id.license, R.id.parking });
						  mylist.setAdapter(listAdapter);

					}
				} else {
                 
				}
			}
		});
	
	}

}
