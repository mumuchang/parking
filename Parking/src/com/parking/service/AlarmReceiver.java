package com.parking.service;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.demo.sms.bean.ParkinglotInfo;
import com.parking.R;
import com.parking.reserve.ReserveActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	int number;
	String objectId;
	SharedPreferences.Editor editor;
	SharedPreferences pkInfo;

	@Override
	public void onReceive(Context context, Intent intent) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setSmallIcon(R.drawable.time);
		// 点击通知后自动清除
		builder.setTicker("预约时间到");
		builder.setAutoCancel(true);
		builder.setContentTitle("预约时间到");
		builder.setContentText("预约已自动取消");
		// 另一种设置铃声的方法
		Notification notify = builder.build();
		// 调用系统默认铃声
		// 使用默认的声音、振动、闪光
		notify.defaults = Notification.DEFAULT_ALL;
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
		pkInfo = context.getSharedPreferences("pkInfo", context.MODE_PRIVATE);
		editor = pkInfo.edit();
		objectId = pkInfo.getString("pkId", "");
		BmobQuery<ParkinglotInfo> bmobQuery = new BmobQuery<ParkinglotInfo>();
		bmobQuery.getObject(objectId, new QueryListener<ParkinglotInfo>() {
			public void done(ParkinglotInfo object, BmobException e) {
				if (e == null) {
					number = object.getCurrentLeftNum();
					update();
				} else {

				}
			}
		});
	}

	protected void update() {
		ParkinglotInfo pk = new ParkinglotInfo();
		pk.setCurrentLeftNum(number + 1);
		editor.putString("pkId", "");
		editor.commit();
		pk.update(objectId, new UpdateListener() {

			@Override
			public void done(BmobException arg0) {
				// TODO 自动生成的方法存根
				if (arg0 == null) {

				}
			}

		});
	}

}
