package com.bmob.demo.sms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.demo.sms.bean.User;
import com.parking.R;


/**  
 * 登注册
 * @class  RegisterActivity  
 * @author smile   
 * @date   2015-6-5 上午11:16:04  
 *   
 */
public class RegisterActivity extends BaseActivity{
	
	
	ImageView iv_left;
	TextView tv_title;
	EditText et_account;
	EditText et_password;
	EditText et_pwd_again;
	Button btn_register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_left.setVisibility(View.VISIBLE);
		iv_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}	
		});
		et_account=(EditText) findViewById(R.id.et_account);
		et_password=(EditText) findViewById(R.id.et_password);
		et_pwd_again=(EditText) findViewById(R.id.et_pwd_again);
		btn_register=(Button)findViewById(R.id.btn_register);
	    btn_register.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				registerUser();
			}
	    	 	
	    });
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("注册");
	}

	
	/**
	 */
	private void registerUser(){
		String account = et_account.getText().toString();
		String password = et_password.getText().toString();
		String pwd = et_pwd_again.getText().toString();
		if (TextUtils.isEmpty(account)) {
			showToast("用户名不能为空");
			return;
		}
		String acc=account.replace(" ", "");
		if(TextUtils.isEmpty(acc)||(!acc.equals(account))){
			showToast("用户名不能包含空格");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			showToast("密码不能为空");
			return;
		}
		if (!password.equals(pwd)) {
			showToast("两次密码不一样");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在登录中...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final User user = new User();
		user.setUsername(account);
		user.setPassword(password);
		user.signUp(new SaveListener<User>() {

			@Override
			public void done(User arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
				progress.dismiss();
				toast("注册成功---用户名："+user.getUsername());
			    Intent intent = new Intent(RegisterActivity.this,LoginMainActivity.class);
				intent.putExtra("from", "login");
				startActivity(intent);
				finish();
			}
			
		});
	}
	
	
}
