package com.parking.service;

import com.parking.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

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
		 //另一种设置铃声的方法
	    Notification notify = builder.build();
		   //调用系统默认铃声
		//使用默认的声音、振动、闪光
		notify.defaults = Notification.DEFAULT_ALL;
		
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, builder.build());
	}

}
