package com.parking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import com.bmob.demo.sms.bean.License;
import com.bmob.demo.sms.bean.User;
import com.parking.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LicenseActivity extends Activity {

	TextView back;
	TextView addlicense;
	SimpleAdapter listAdapter;
	ListView mylist;
	List<HashMap<String, String>> list;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.license);
		addlicense = (TextView) findViewById(R.id.addlicense);
		addlicense.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(LicenseActivity.this,
						AddLicense.class);
				startActivity(intent);
			}

		});
		back = (TextView) findViewById(R.id.tv_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		mylist=(ListView) findViewById(R.id.mylist);
	    list=new ArrayList<HashMap<String,String>>();
	}
	
	private void init() {
		// TODO 自动生成的方法存根
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
					      list.add(map);
					}
					listAdapter = new SimpleAdapter(LicenseActivity.this, list,
							R.layout.item, new String[] { "license" },
							new int[] { R.id.items });
					mylist.setAdapter(listAdapter);
				} else {

				}
			}
		});
	}

	public void onResume() {
		super.onResume();
		list.removeAll(list);
		init();
	}
}
