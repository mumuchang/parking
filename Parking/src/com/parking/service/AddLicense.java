package com.parking.service;

import cn.bmob.v3.BmobUser;

import com.bmob.dao.SaveBmob;
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
				// TODO �Զ����ɵķ������
				saveLicense();
			}

		});
		back=(TextView) findViewById(R.id.tv_left);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
	                	finish();
			}
		});
	}

	protected void saveLicense() {
		// TODO �Զ����ɵķ������
		   SaveBmob save=new SaveBmob();
		   lic = license.getText().toString();
		   String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
		   if (TextUtils.isEmpty(lic)){
			   Toast.makeText(AddLicense.this, "���ƺŲ���Ϊ��", Toast.LENGTH_LONG).show();
			   return;
		   }
		   if( !lic.matches(carnumRegex)){
			   Toast.makeText(AddLicense.this, "���ƺŲ��Ϸ�", Toast.LENGTH_LONG).show();
			   return;
		   }
			User user = BmobUser.getCurrentUser(User.class);
			License ss=new License();
			if (user != null) {
				ss.setUsername(user.getUsername());
				ss.setLicense(lic);
				if(save.saveLicense(ss)){
						Toast.makeText(AddLicense.this, "��ӳɹ�", Toast.LENGTH_LONG)
						.show();
				}else{
					   Toast.makeText(AddLicense.this, "���ʧ��", Toast.LENGTH_LONG)
					   .show();
				}
			} else {
				Toast.makeText(AddLicense.this, "���ȵ�¼", Toast.LENGTH_LONG)
						.show();
			}      
	}
}
