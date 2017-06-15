package com.parking.reserve;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.dao.QueryBmob;
import com.bmob.dao.SaveBmob;
import com.bmob.demo.sms.bean.ParkinglotInfo;
import com.bmob.demo.sms.bean.Record;
import com.bmob.demo.sms.bean.User;
import com.parking.R;
import com.parking.service.AlarmReceiver;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

public class ReserveActivity extends Activity {

	SpinerPopWindow<String> mSpinerPopWindow;
	List<String> list;
	TextView tvValue;
	String name2;
	String parkingName;
	String total = "40";
	String remain;// ������
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
	int number;// ����
	int count = 1;
	int startHour2;
	int startMin2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reserve);

		initData();
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
		priceView.setText("�۸�:" + price + "Ԫ/Сʱ");
		remainView.setText("�ճ�λ��" + remain);
		totalView.setText("�ܳ�λ��" + total);
		text = (TimeTextView) findViewById(R.id.text);
		hour = (EditText) findViewById(R.id.hour);
		minute = (EditText) findViewById(R.id.minute);
		reserve = (Button) findViewById(R.id.reserve);
		tvValue = (TextView) findViewById(R.id.value);
		mSpinerPopWindow = new SpinerPopWindow<String>(this, list,
				itemClickListener);
		mSpinerPopWindow.setOnDismissListener(dismissListener);
		tvValue.setOnClickListener(clickListener);
		cancle = (Button) findViewById(R.id.cancle);
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				deleteTable();
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
				// TODO �Զ����ɵķ������
				SharedPreferences pkInfo = getSharedPreferences("pkInfo",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = pkInfo.edit();
				editor.putString("pkId", "");
				editor.commit();
				name2 = pkInfo.getString("name", "");
				stop.setVisibility(View.INVISIBLE);
				cancle.setVisibility(View.INVISIBLE);
				text.setText("�����κ�ԤԼ");
				Date date = new Date();
				long t2 = date.getTime();
				text.setTimes(t2);
				cancleAlarm();
				addRecord(name2);

			}

		});
		reserve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO �Զ����ɵķ������
				addReserveInfo();
			}

		});

	}

	/**
	 * ����popupwindowȡ��
	 */
	private OnDismissListener dismissListener = new OnDismissListener() {
		@Override
		public void onDismiss() {
			setTextImage(R.drawable.icon_down);
		}
	};

	/**
	 * popupwindow��ʾ��ListView��item����¼�
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mSpinerPopWindow.dismiss();
			tvValue.setText(list.get(position));
			Toast.makeText(ReserveActivity.this, "�����:" + position,
					Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * ��ʾPopupWindow
	 */
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.value:
				mSpinerPopWindow.setWidth(tvValue.getWidth());
				mSpinerPopWindow.showAsDropDown(tvValue);
				setTextImage(R.drawable.icon_up);
				break;
			}
		}
	};

	/**
	 * ��ʼ������
	 */
	private void initData() {
		list = new ArrayList<String>();
		QueryBmob qb = new QueryBmob();
		list = qb.queryLicense();
		Toast.makeText(ReserveActivity.this,list.toString(), Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��TextView�ұ�����ͼƬ
	 * 
	 * @param resId
	 */
	private void setTextImage(int resId) {
		Drawable drawable = getResources().getDrawable(resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());// ��������ͼƬ��С��������ʾ
		tvValue.setCompoundDrawables(null, null, drawable, null);
	}

	protected void addRecord(String name) {
		// TODO �Զ����ɵķ������
		// ���복�ƺ�
		SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		Record rr = new Record();
		User user = BmobUser.getCurrentUser(User.class);
		rr.setUsername(user.getUsername());
		rr.setParkingName(pkInfo.getString("name", ""));
		rr.setLicense(pkInfo.getString("license", ""));
		SaveBmob save = new SaveBmob();
		save.saveRecord(rr);

	}

	private void addReserveInfo() {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date curDate = new Date(System.currentTimeMillis());
		String startTime = format.format(curDate);
		String str[] = startTime.split(":");
		String startHour = str[0];
		String startMin = str[1];
		startHour2 = Integer.parseInt(startHour);
		startMin2 = Integer.parseInt(startMin);
		if (hour.getText().toString().equals("")
				|| minute.getText().toString().equals("")) {
			Toast.makeText(ReserveActivity.this, "ʱ�䲻��Ϊ��", Toast.LENGTH_SHORT)
					.show();
		} else {
			endHour = Integer.parseInt(hour.getText().toString());
			endMin = Integer.parseInt(minute.getText().toString());
			if (!text.getText().equals("�����κ�ԤԼ")) {
				Toast.makeText(ReserveActivity.this, "��ǰ����ԤԼ",
						Toast.LENGTH_LONG).show();
			} else if (endHour < startHour2) {
				Toast.makeText(ReserveActivity.this, "ԤԼʱ�䲻�����ڵ�ǰʱ��",
						Toast.LENGTH_LONG).show();
			} else if (endHour == startHour2 && startMin2 >= endMin) {
				Toast.makeText(ReserveActivity.this, "ԤԼʱ�䲻�����ڵ�ǰʱ��",
						Toast.LENGTH_LONG).show();
			} else if ((endHour - startHour2) > 2) {
				Toast.makeText(ReserveActivity.this, "ԤԼʱ�䲻�ɳ�������Сʱ",
						Toast.LENGTH_LONG).show();
			} else if (tvValue.getText().equals("��ѡ���ƺ�")) {
				Toast.makeText(ReserveActivity.this, "��ѡ���ƺ�",
						Toast.LENGTH_LONG).show();
			} else {
				// ���и���
				addToTable();
			}
		}
	}

	private void startAlarm() {
		// TODO �Զ����ɵķ������
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		long triggerAtTime = SystemClock.elapsedRealtime() + recLen * 1000;
		// �˴����ÿ���AlarmReceiver���Service
		Intent i = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		// ELAPSED_REALTIME_WAKEUP��ʾ�ö�ʱ����ĳ���ʱ���ϵͳ�������𣬲��һỽ��CPU��
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
	}

	protected void cancleAlarm() {
		// TODO �Զ����ɵķ������
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(ReserveActivity.this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(ReserveActivity.this, 0,
				i, 0);
		manager.cancel(pi);
	}

	private void addToTable() {
		// TODO �Զ����ɵķ������

		BmobQuery<ParkinglotInfo> query = new BmobQuery<ParkinglotInfo>();
		query.addWhereEqualTo("parkinglot_name", parkingName);
		query.findObjects(new FindListener<ParkinglotInfo>() {

			@Override
			public void done(List<ParkinglotInfo> arg0, BmobException arg1) {
				// TODO �Զ����ɵķ������
				if (arg1 == null) {
					for (ParkinglotInfo pk : arg0) {

						objectId = pk.getObjectId();
						number = pk.getCurrentLeftNum();
						if (number != 0) {
							update("add");
						} else {
							Toast.makeText(ReserveActivity.this, "�ճ�λΪ0,�޷�ԤԼ",
									Toast.LENGTH_LONG).show();
						}

					}
				} else {
					Log.i("bmob",
							"ʧ�ܣ�" + arg1.getMessage() + ","
									+ arg1.getErrorCode());
				}
			}

		});

	}

	public void update(String type) {
		// TODO �Զ����ɵķ������
		final String type1 = type;
		SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		ParkinglotInfo pk = new ParkinglotInfo();
		if (type1.equals("add")) {
			pk.setCurrentLeftNum(number - 1);
			editor.putString("pkId", objectId);
		} else {
			pk.setCurrentLeftNum(number + 1);// ����-1
			editor.putString("pkId", "");
		}
		editor.commit();
		pk.update(objectId, new UpdateListener() {

			@Override
			public void done(BmobException arg0) {
				// TODO �Զ����ɵķ������
				if (arg0 == null) {
					Toast.makeText(ReserveActivity.this, "���³ɹ�",
							Toast.LENGTH_SHORT).show();
					if (type1.equals("add")) {
						SharedPreferences pkInfo = getSharedPreferences(
								"pkInfo", MODE_PRIVATE);
						SharedPreferences.Editor editor = pkInfo.edit();
						cancle.setVisibility(View.VISIBLE);
						stop.setVisibility(View.VISIBLE);
						Long end = (long) (endHour * (3600 * 1000) + endMin
								* (60 * 1000));
						Long start = (long) (startHour2 * (3600 * 1000) + startMin2
								* (60 * 1000));
						recLen = (end - start) / 1000;
						Date date = new Date();
						long t2 = date.getTime();
						editor.putLong("time", t2 + recLen * 1000);
						editor.putString("name", parkingName);// ����ͣ��������
						editor.putString("license", tvValue.getText()
								.toString());// ���泵�ƺ�
						editor.commit();
						startAlarm();
						text.setTimes(t2 + recLen * 1000);
					}
				} else {
					Toast.makeText(ReserveActivity.this, "����ʧ��",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

	}

	// ����ԤԼʱ�� ԤԼȡ��
	private void deleteTable() {
		// TODO �Զ����ɵķ������
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
					text.setText("�����κ�ԤԼ");
				} else {

				}
			}
		});

	}

	public void onResume() {
		
		super.onResume();
		SharedPreferences pkInfo = getSharedPreferences("pkInfo", MODE_PRIVATE);
		SharedPreferences.Editor editor = pkInfo.edit();
		objectId = pkInfo.getString("pkId", "");
		if (!objectId.equals("")) {
			Long time = pkInfo.getLong("time", 0);
			Toast.makeText(ReserveActivity.this, "time" + time,
					Toast.LENGTH_SHORT).show();
			Date date = new Date();
			long t2 = date.getTime();
			if (t2 < time) {
				cancle.setVisibility(View.VISIBLE);
				stop.setVisibility(View.VISIBLE);
				text.setTimes(time);
			} else {
				// deleteTable();
				cancle.setVisibility(View.GONE);
				stop.setVisibility(View.GONE);
				text.setText("�����κ�ԤԼ");
			}
		}

	}

}
