package com.bmob.dao;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.demo.sms.bean.License;
import com.bmob.demo.sms.bean.Record;
import com.bmob.demo.sms.bean.Suggestion;

public class SaveBmob {

	public boolean saveRecord(Record record) {
		record.save(new SaveListener<String>() {
			@Override
			public void done(String arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
			}
		});
		return true;
	}

	public boolean saveSuggest(Suggestion suggest) {
		suggest.save(new SaveListener<String>() {
			@Override
			public void done(String arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
			}
		});
		return true;
	}

	public boolean saveLicense(License license) {
		license.save(new SaveListener<String>() {
			@Override
			public void done(String arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
			}
		});
		return true;
	}
}
