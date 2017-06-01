package com.parking.service;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.demo.sms.bean.License;
import com.bmob.demo.sms.bean.User;
import com.parking.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddLicense extends Activity {

	Button save;
	EditText license;
	String lic;
	TextView back;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addlicense);
		license = (EditText) findViewById(R.id.tv_license);
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				saveLicense();
			}

		});
		back=(TextView) findViewById(R.id.tv_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
	                	finish();
			}
		});
	}

	protected void saveLicense() {
		// TODO 自动生成的方法存根
		   lic = license.getText().toString();
		   String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
		   if (TextUtils.isEmpty(lic)){
			   Toast.makeText(AddLicense.this, "车牌号不能为空", Toast.LENGTH_LONG).show();
			   return;
		   }
		   if( !lic.matches(carnumRegex)){
			   Toast.makeText(AddLicense.this, "车牌号不合法", Toast.LENGTH_LONG).show();
			   return;
		   }
			User user = BmobUser.getCurrentUser(User.class);
			License ss=new License();
			if (user != null) {
				ss.setUsername(user.getUsername());
				ss.setLicense(lic);
				ss.save(new SaveListener<String>() {
					@Override
					public void done(String arg0, BmobException arg1) {
						// TODO 自动生成的方法存根
						Toast.makeText(AddLicense.this, "添加成功", Toast.LENGTH_LONG)
						.show();
					}
				});
			} else {
				Toast.makeText(AddLicense.this, "请先登录", Toast.LENGTH_LONG)
						.show();
			}      
	}
}
