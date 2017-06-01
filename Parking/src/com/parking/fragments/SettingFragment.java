package com.parking.fragments;

import cn.bmob.v3.BmobUser;

import com.bmob.demo.sms.LoginActivity;
import com.bmob.demo.sms.bean.User;
import com.parking.R;
import com.parking.service.LicenseActivity;
import com.parking.service.RecordActivity;
import com.parking.service.SuggestActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingFragment extends Fragment {

	TextView name;
	TextView suggest;
	Button btn;
	TextView license;
    TextView record;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 引入我们的布局
		return inflater.inflate(R.layout.tab03, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		name = (TextView) getActivity().findViewById(R.id.name);
		license = (TextView) getActivity().findViewById(R.id.license);
		suggest = (TextView) getActivity().findViewById(R.id.suggest);
        record=(TextView) getActivity().findViewById(R.id.record);
        record.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), RecordActivity.class);
				startActivity(intent);
			}
        	
        	
        	
        });
		User user = BmobUser.getCurrentUser(User.class);
		if (user != null) {
			name.setText(user.getUsername());
		}
		btn = (Button) getActivity().findViewById(R.id.denglu);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		license.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), LicenseActivity.class);
				startActivity(intent);
			}

		});
		suggest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), SuggestActivity.class);
				startActivity(intent);
			}

		});
	}

}
