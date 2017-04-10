package com.bmob.demo.sms;

import com.bmob.demo.sms.bean.User;
import com.parking.R;
import com.parking.main.MainActivity;

import cn.bmob.v3.BmobUser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class LoginMainActivity extends Activity {

	ImageView iv_left;
	TextView tv_title;
	TextView tv_user;
	Button btn_bind;
	Button btn_reset;
	String from;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginmain);
		tv_user=(TextView) findViewById(R.id.tv_user);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_left.setVisibility(View.VISIBLE);
		iv_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent=new Intent(LoginMainActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}	
		});
		btn_bind=(Button) findViewById(R.id.btn_bind);
		btn_bind.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent=new Intent(LoginMainActivity.this,UserBindPhoneActivity.class);
				startActivity(intent);
			}
		});
		btn_reset=(Button)findViewById(R.id.btn_reset);
		btn_reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent intent=new Intent(LoginMainActivity.this,ResetPasswordActivity.class);
				startActivity(intent);
			}
		});
		from = getIntent().getStringExtra("from");
		if(from.equals("login")){
			btn_bind.setVisibility(View.VISIBLE);
		}else{
			btn_bind.setVisibility(View.GONE);
		}
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("首页");
	}

	private void UpdateUser(){
		User user = BmobUser.getCurrentUser(User.class);
		//用户只有绑定过手机号或者用手机号注册登录过就可以直接通过手机号码来重置用户密码了，所以加下这个判断
		if(user!=null && user.getMobilePhoneNumberVerified()!=null && user.getMobilePhoneNumberVerified()){
			btn_reset.setVisibility(View.VISIBLE);
		}else{
			btn_reset.setVisibility(View.INVISIBLE);
		}
		tv_user.setText(user.getUsername()+"-"+"-"+user.getMobilePhoneNumberVerified()+"-"+user.getMobilePhoneNumber());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UpdateUser();
	}
	
}