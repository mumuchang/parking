package com.bmob.demo.sms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.demo.sms.bean.User;
import com.parking.R;

/**  
 * 绑定手机号  
 * @class  UserBindPhoneActivity  
 * @author smile   
 * @date   2015-6-5 下午3:08:53  
 *   
 */
public class UserBindPhoneActivity extends BaseActivity {
	
	ImageView iv_left;
	EditText et_number;
	EditText et_input;
	TextView tv_title;
	TextView tv_send;
	TextView tv_bind;
	MyCountTimer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind);
		iv_left=(ImageView) findViewById(R.id.iv_left);
		iv_left.setVisibility(View.VISIBLE);
		iv_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}	
		});
		 tv_bind=(TextView) findViewById(R.id.tv_bind);  
		 tv_bind.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					verifyOrBind();
				}
		    	 	
		    });
		 tv_send=(TextView) findViewById(R.id.tv_send);  
		 tv_send.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					requestSMSCode();
				}
		    	 	
		    });
	    et_input=(EditText) findViewById(R.id.et_input);
		et_input=(EditText) findViewById(R.id.et_input);
		et_number=(EditText) findViewById(R.id.et_number);
		iv_left.setVisibility(View.VISIBLE);
		tv_title=(TextView) findViewById(R.id.tv_title);
		tv_title.setText("绑定手机号");
	}
	
	class MyCountTimer extends CountDownTimer{  
		  
        public MyCountTimer(long millisInFuture, long countDownInterval) {  
            super(millisInFuture, countDownInterval);  
        }  
		@Override  
        public void onTick(long millisUntilFinished) {  
			tv_send.setText((millisUntilFinished / 1000) +"秒后重发");  
        }  
        @Override  
        public void onFinish() {  
        	tv_send.setText("重新发送验证码");  
        }  
    }  
		
	private void requestSMSCode() {
		String number = et_number.getText().toString();
		if (!TextUtils.isEmpty(number)) {
			timer = new MyCountTimer(60000, 1000);   
			timer.start();
			BmobSMS.requestSMSCode(number, "手机号码登陆模板",new QueryListener<Integer>() {
				@Override
				public void done(Integer arg0, BmobException arg1) {
					// TODO 自动生成的方法存根
					// TODO 自动生成的方法存根
					// TODO Auto-generated method stub
					if (arg1 == null) {// 验证码发送成功
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
	private void verifyOrBind(){
		final String phone = et_number.getText().toString();
		String code = et_input.getText().toString();
		if (TextUtils.isEmpty(phone)) {
			showToast("手机号码不能为空");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			showToast("验证码不能为空");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("正在验证短信验证码...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		// V3.3.9提供的一键注册或登录方式，可传手机号码和验证码
		BmobSMS.verifySmsCode(phone, code, new UpdateListener() {		
			@Override
			public void done(BmobException ex) {
				// TODO Auto-generated method stub
				progress.dismiss();
				if(ex==null){
					toast("手机号码已验证");
					bindMobilePhone(phone);
				}else{
					toast("验证失败：code="+ex.getErrorCode()+"，错误描述："+ex.getLocalizedMessage());
				}
			}
		});
	}
	
	private void bindMobilePhone(String phone){
		//开发者在给用户绑定手机号码的时候需要提交两个字段的值：mobilePhoneNumber、mobilePhoneNumberVerified
		User user =new User();
		user.setMobilePhoneNumber(phone);
		user.setMobilePhoneNumberVerified(true);
		User cur = BmobUser.getCurrentUser(User.class);
		user.update( cur.getObjectId(),new UpdateListener() {
			@Override
			public void done(BmobException arg0) {
				// TODO 自动生成的方法存根
				toast("手机号码绑定成功");
				finish();
			}
		});
	}
	
}
