package com.bmob.demo.sms;

import com.parking.R;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * 重置密码
 * @class ResetPasswordActivity
 * @author smile
 * @date 2015-6-5 上午11:23:44
 * 
 */
public class ResetPasswordActivity extends BaseActivity {

	MyCountTimer timer;
	ImageView iv_left;
	TextView tv_title;
	EditText et_phone;
	EditText et_code;
	Button btn_send;
	EditText et_pwd;
	Button btn_reset;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pwd);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_left.setVisibility(View.VISIBLE);
		iv_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}	
		});
		btn_send=(Button)findViewById(R.id.btn_send);
	    btn_send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				requestSMSCode();
			}
	    	 	
	    });
		btn_reset=(Button)findViewById(R.id.btn_reset);
	    btn_reset.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				resetPwd();
			}
	    	 	
	    });
		et_code=(EditText) findViewById(R.id.et_verify_code);
		et_phone=(EditText) findViewById(R.id.et_phone);
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		iv_left.setVisibility(View.VISIBLE);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("重置密码");
	}

	class MyCountTimer extends CountDownTimer{  
		  
        public MyCountTimer(long millisInFuture, long countDownInterval) {  
            super(millisInFuture, countDownInterval);  
        }  
		@Override  
        public void onTick(long millisUntilFinished) {  
            btn_send.setText((millisUntilFinished / 1000) +"秒后重发");  
        }  
        @Override  
        public void onFinish() {  
        	btn_send.setText("重新发送验证码");  
        }  
    }  
	
	private void requestSMSCode() {
		String number = et_phone.getText().toString();
		if (!TextUtils.isEmpty(number)) {
			timer = new MyCountTimer(60000, 1000);   
			timer.start();   
			BmobSMS.requestSMSCode(number, "重置密码模板",new QueryListener<Integer>() {
				@Override
				public void done(Integer arg0, BmobException arg1) {
					// TODO 自动生成的方法存根
					if (arg1== null) {// 验证码发送成功
						toast("验证码发送成功");// 用于查询本次短信发送详情
					}else{//如果验证码发送错误，可停止计时
						timer.cancel();
					}
				
				}
			});
		} else {
			toast("请输入手机号码");
		}
	}
	/**
	 */
	private void resetPwd() {
		final String code = et_code.getText().toString();
		final String pwd = et_pwd.getText().toString();
		if (TextUtils.isEmpty(code)) {
			showToast("验证码不能为空");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			showToast("密码不能为空");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(ResetPasswordActivity.this);
		progress.setMessage("正在重置密码...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		// V3.3.9提供的重置密码功能，只需要输入验证码和新密码即可
		BmobUser.resetPasswordBySMSCode(code, pwd, new UpdateListener() {
			
			@Override
			public void done(BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if(ex==null){
					toast("密码重置成功");
					finish();
				}else{
					toast("密码重置失败：code="+ex.getErrorCode()+"，错误描述："+ex.getLocalizedMessage());
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(timer!=null){
			timer.cancel();
		}
	}
}
