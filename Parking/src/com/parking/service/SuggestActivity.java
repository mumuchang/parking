package com.parking.service;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.demo.sms.bean.Suggestion;
import com.bmob.demo.sms.bean.User;
import com.parking.R;
import com.parking.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestActivity extends Activity {

	TextView commit;
	EditText suggestion;
	ImageView back;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suggest);
		
		back=(ImageView) findViewById(R.id.iv_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
	                	finish();
			}
		});
		commit = (TextView) findViewById(R.id.commit);
		suggestion = (EditText) findViewById(R.id.suggestion);
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				sendMessage();
			}
		});
	}

	protected void sendMessage() {
		// TODO 自动生成的方法存根
		String str;
		str = suggestion.getText().toString();
		Suggestion ss = new Suggestion();
		String str2 = str.replace(" ", "");
		if (TextUtils.isEmpty(str2)) {
			Toast.makeText(SuggestActivity.this, "建议不能为空", Toast.LENGTH_LONG)
					.show();
			return;
		}
		User user = BmobUser.getCurrentUser(User.class);
		if (user != null) {
			ss.setUsername(user.getUsername());
			ss.setSuggest(str);
			ss.save(new SaveListener<String>() {
				@Override
				public void done(String arg0, BmobException arg1) {
					// TODO 自动生成的方法存根
					Toast.makeText(SuggestActivity.this, "发表成功", Toast.LENGTH_LONG)
					.show();
				}
			});
		} else {
			Toast.makeText(SuggestActivity.this, "请先登录", Toast.LENGTH_LONG)
					.show();
		}

	}
}
