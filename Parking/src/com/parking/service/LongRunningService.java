package com.parking.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class LongRunningService extends Service {

	Long time;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.i("info", "Service--onCreate()");
		super.onCreate();
		
	}
	@Override
	 public int onStartCommand(Intent intent, int flags, int startId) { 
	  
	  AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE); 
	  //读者可以修改此处的Minutes从而改变提醒间隔时间 
	  //此处是设置每隔90分钟启动一次 
	  //这是90分钟的毫秒数 
	  int Minutes = 90*60*1000; 
	  //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间 
	  long triggerAtTime = SystemClock.elapsedRealtime() + Minutes; 
	  //此处设置开启AlarmReceiver这个Service 
	  Intent i = new Intent(this, AlarmReceiver.class); 
	  PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); 
	  //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。 
	  manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi); 
	  return super.onStartCommand(intent, flags, startId); 
	 } 
	  
	 @Override
	 public void onDestroy() { 
	  super.onDestroy(); 
	  //在Service结束后关闭AlarmManager 
	  AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE); 
	  Intent i = new Intent(this, AlarmReceiver.class); 
	  PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0); 
	  manager.cancel(pi); 
	  
	 } 
}
