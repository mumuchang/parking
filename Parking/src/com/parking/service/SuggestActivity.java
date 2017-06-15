package com.parking.service;

import cn.bmob.v3.BmobUser;

import com.bmob.dao.SaveBmob;
import com.bmob.demo.sms.bean.Suggestion;
import com.bmob.demo.sms.bean.User;
import com.parking.R;

import android.app.Activity;
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

		back = (ImageView) findViewById(R.id.iv_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				finish();
			}
		});
		commit = (TextView) findViewById(R.id.commit);
		suggestion = (EditText) findViewById(R.id.suggestion);
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				sendMessage();
			}
		});
	}

	protected void sendMessage() {
		// TODO �Զ����ɵķ������
		SaveBmob save = new SaveBmob();
		String str;
		str = suggestion.getText().toString();
		Suggestion ss = new Suggestion();
		String str2 = str.replace(" ", "");
		if (TextUtils.isEmpty(str2)) {
			Toast.makeText(SuggestActivity.this, "���鲻��Ϊ��", Toast.LENGTH_LONG)
					.show();
			return;
		}
		User user = BmobUser.getCurrentUser(User.class);
		if (user != null) {
			ss.setUsername(user.getUsername());
			ss.setSuggest(str);
			if (save.saveSuggest(ss)) {
				Toast.makeText(SuggestActivity.this, "����ɹ�", Toast.LENGTH_LONG)
						.show();
			} else {

				Toast.makeText(SuggestActivity.this, "����ʧ��", Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Toast.makeText(SuggestActivity.this, "���ȵ�¼", Toast.LENGTH_LONG)
					.show();
		}

	}
}
