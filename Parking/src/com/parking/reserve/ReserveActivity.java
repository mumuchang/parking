package com.parking.reserve;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.demo.sms.bean.ParkinglotInfo;
import com.parking.R;
import com.parking.service.AlarmReceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReserveActivity extends Activity {

	// CustomDialog.Builder builder;
	String parkingName;
	String total = "40";
	String remain;// 不可用
	String price;
	TextView name;
	TextView totalView;
	TextView remainView;
	TextView priceView;
	EditText hour;
	EditText minute;
	Button reserve;
	Button cancle;
	Button stop;
	TimeTextView text;
	int endHour;
	int endMin;
	Long recLen;
	String objectId;
	int number;// 可用
	int count = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reserve);

		name = (TextView) findViewById(R.id.parkingName);
		totalView = (TextView) findViewById(R.id.total);
		remainView = (TextView) findViewById(R.id.remain);
		priceView = (TextView) findViewById(R.id.price);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("android.intent.extra.INTENT");
		parkingName = bundle.getString("name");
		price = bundle.getInt("price") + "";
		remain = bundle.getInt("currentLeftNum") + "";
		name.setText(parkingName);
		priceView.setText("价格:"+price+"元/小时");
		remainView.setText("空车位："+remain);
		totalView.setText("总车位："+total);
		text = (TimeTextView) findViewById(R.id.text);
		hour = (EditText) findViewById(R.id.hour);
		minute = (EditText) findViewById(R.id.minute);
		reserve = (Button) findViewById(R.id.reserve);
		cancle = (Button) findViewById(R.id.cancle);
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				deleteTable();
				// timer.cancel();
				Date date = new Date();
				long t2 = date.getTime();
				text.setTimes(t2);
				cancleAlarm();
			}
		});
		stop = (Button) findViewById(R.id.stop);
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				SharedPreferences pkInfo = getSharedPreferences("pkInfo",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = pkInfo.edit();
				editor.putString("pkId", "");
				editor.commit();
				stop.setVisibility(View.INVISIBLE);
				cancle.setVisibility(View.INVISIBLE);
				text.setText("暂无任何预约");
				Date date = new Date();
				long t2 = date.getTime();
				text.setTimes(t2);
				cancleAlarm();
				
				// count = 0;
				// timer.cancel();
			}

		});
		/*
		 * builder = new CustomDialog.Builder(this);
		 * builder.setMessage("您的预约时间已到未到达 预约已取消 请再次预定");
		 * builder.setTitle("提示"); builder.setPositiveButton("确定", new
		 * DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int which) { dialog.dismiss(); //
		 * 设置你的操作事项 } });
		 * 
		 * builder.setNegativeButton("取消", new
		 * android.content.DialogInterface.OnClickListener() { public void
		 * onClick(DialogInterface dialog, int which) { dialog.dismiss(); } });
		 */
		reserve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO 自动生成的方法存根
				if (addReserveInfo() == false) {

				}
			}

		});

	}

	private boolean addReserveInfo() {
		// TODO 自动生成的方法存根
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String startTime = format.format(curDate);
		String str[] = startTime.split(":");
		String startHour = str[0];
		String startMin = str[1];
		int startHour2 = Integer.parseInt(startHour);
		int startMin2 = Integer.parseInt(startMin);
		if (hour.getText().toString() == null
				|| minute.getText().toString() == null) {
			Toast.makeText(ReserveActivity.this, "时间不能为空", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		endHour = Integer.parseInt(hour.getText().toString());
		endMin = Integer.parseInt(minute.getText().toString());

		if (!text.getText().equals("暂无任何预约")) {
			Toast.makeText(ReserveActivity.this, "当前已有预约", Toast.LENGTH_LONG)
					.show();
		} else if (endHour < startHour2) {
			Toast.makeText(ReserveActivity.this, "预约时间不可早于当前时间",
					Toast.LENGTH_LONG).show();
		} else if (endHour == startHour2 && startMin2 >= endMin) {
			Toast.makeText(ReserveActivity.this, "预约时间不可早于当前时间",
					Toast.LENGTH_LONG).show();
		} else if ((endHour - startHour2) > 2) {
			Toast.makeText(ReserveActivity.this, "预约时间不可超过两个小时",
					Toast.LENGTH_LONG).show();
		} else {
			// 表中更新
			addToTable();
			// Toast.makeText(ReserveActivity.this, "预约成功", Toast.LENGTH_LONG)
			// .show();
			cancle.setVisibility(View.VISIBLE);
			stop.setVisibility(View.VISIBLE);
			Long end = (long) (endHour * (3600 * 1000) + endMin * (60 * 1000));
			Long start = (long) (startHour2 * (3600 * 1000) + startMin2
					* (60 * 1000));
			recLen = (end - start) / 1000;
			Date date = new Date();
			long t2 = date.getTime();
			SharedPreferences pkInfo = getSharedPreferences("pkInfo",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = pkInfo.edit();
			editor.putLong("time", t2 + recLen*1000);
			// new Thread(new MyThread()).start();
			editor.commit();
			startAlarm();
			text.setTimes(t2 +  recLen*1000);
			// startCountDownTime(recLen);

		}
		return true;
	}

	private void startAlarm() {
		// TODO 自动生成的方法存根
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		long triggerAtTime = SystemClock.elapsedRealtime() + recLen*1000;
		// 此处设置开启AlarmReceiver这个Service
		Intent i = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		// ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
	}

	protected void cancleAlarm() {
		// TODO 自动生成的方法存根
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(ReserveActivity.this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(ReserveActivity.this, 0,
				i, 0);
		manager.cancel(pi);
	}

	private void startCountDownTime(long time) {
		/**
		 * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
		 * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。 有onTick，onFinsh、cancel和start方法
		 */
		CountDownTimer timer = new CountDownTimer(time * 1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				// 每隔countDownInterval秒会回调一次onTick()方法
				text.setText(millisUntilFinished / 1000 + "s");
			}

			@Override
			public void onFinish() {
				text.setText("暂无任何预约");
				deleteTable();
				// builder.create().show();
				cancle.setVisibility(View.GONE);
				stop.setVisibility(View.GONE);
			}
		};
		timer.start();// 开始计时

	}

	private void addToTable() {
		// TODO 自动生成的方法存根

		BmobQuery<ParkinglotInfo> query = new BmobQuery<ParkinglotInfo>();
		query.addWhereEqualTo("parkinglot_name", parkingName);
		query.findObjects(new FindListener<ParkinglotInfo>() {

			@Override
			public void done(List<ParkinglotInfo> arg0, BmobException arg1) {
				// TODO 自动生成的方法存根
				if (arg1 == null) {
					Toast.makeText(ReserveActivity.this,
							"查询成功：共" + arg0.size() + "条数据。", Toast.LENGTH_LONG)
							.show();
					for (ParkinglotInfo pk : arg0) {

						objectId = pk.getObjectId();
						number = pk.getCurrentLeftNum();
						Toast.makeText(ReserveActivity.this, number + "",
								Toast.LENGTH_LONG).show();
						update("add");

					}
				} else {
					Log.i("bmob",
							"失败：" + arg1.getMessage() + ","
									+ arg1.getErrorCode());
				}
			}

		});

	}

	public void update(String type) {
		// TODO 自动生成的方法存根
		SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		Toast.makeText(ReserveActivity.this, number + "", Toast.LENGTH_LONG)
				.show();
		ParkinglotInfo pk = new ParkinglotInfo();
		if (type.equals("add")) {
			pk.setCurrentLeftNum(number - 1);
			editor.putString("pkId", objectId);

		} else {
			pk.setCurrentLeftNum(number + 1);// 表中-1
			editor.putString("pkId", "");
		}
		editor.commit();
		pk.update(objectId, new UpdateListener() {

			@Override
			public void done(BmobException arg0) {
				// TODO 自动生成的方法存根
				if (arg0 == null) {
					Toast.makeText(ReserveActivity.this, "更新成功",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ReserveActivity.this, "更新失败",
							Toast.LENGTH_LONG).show();
				}
			}

		});

	}

	final Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				recLen--;
				text.setText(recLen + "s");
				if (recLen == 0) {
					text.setText("暂无任何预约");
					deleteTable();
					// builder.create().show();
					cancle.setVisibility(View.GONE);
					stop.setVisibility(View.GONE);
					break;
				}

			}
			super.handleMessage(msg);
		}

	};

	// 超出预约时间 预约取消
	private void deleteTable() {
		// TODO 自动生成的方法存根
		SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		objectId = pkInfo.getString("pkId", "");
		BmobQuery<ParkinglotInfo> bmobQuery = new BmobQuery<ParkinglotInfo>();
		bmobQuery.getObject(objectId, new QueryListener<ParkinglotInfo>() {
			public void done(ParkinglotInfo object, BmobException e) {
				if (e == null) {
					number = object.getCurrentLeftNum();
					update("delete");
					cancle.setVisibility(View.GONE);
					stop.setVisibility(View.GONE);
					// count=0;
					text.setText("暂无任何预约");
				} else {

				}
			}
		});

	}
    
	class MyThread implements Runnable {

		public void run() {
			count = 1;
			while (true) {
				try {
					if (count != 0) {
						Thread.sleep(1000); // sleep 1000ms
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					} else {
						text.setText("暂无任何预约");
						break;

					}
				} catch (Exception e) {

				}
			}

		}
	}

    public void onResume(){
    	super.onResume();
    	SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		objectId = pkInfo.getString("pkId", "");
		if (!objectId.equals("")) {
			Long time = pkInfo.getLong("time", 0);
			Toast.makeText(ReserveActivity.this,"time"+time, Toast.LENGTH_LONG).show();
			Date date = new Date();
			long t2 = date.getTime();
			if (t2 < time) {
				cancle.setVisibility(View.VISIBLE);
				stop.setVisibility(View.VISIBLE);
				text.setTimes(time);
			}else{
			    deleteTable();
			}
		}

    }

}
