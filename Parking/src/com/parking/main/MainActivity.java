package com.parking.main;

import cn.bmob.v3.Bmob;


import com.parking.R;
import com.parking.fragments.SettingFragment;
import com.parking.fragments.ShouyeFragment;
import com.parking.fragments.ZhouweiFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements OnClickListener{

	//�ײ���3�������ؼ�
		private LinearLayout mTabShouye;
		private LinearLayout mTabZhouwei;
		private LinearLayout mTabSetting;
		//�ײ�3�������ؼ��е�ͼƬ��ť
		private ImageButton mImgShouye;
		private ImageButton mImgZhouwei;
		private ImageButton mImgSetting;
		//��ʼ��4��Fragment
		private android.app.Fragment tab01;
		private android.app.Fragment tab02;
		private android.app.Fragment tab03;
		
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(MainActivity.this, "19872817e4d9a4070addbbb47c9564f5"); 
        initView();//��ʼ�����е�view
		initEvents();
		setSelect(0);//Ĭ����ʾ��ҳ����
    }



	private void initEvents() {
		// TODO Auto-generated method stub
		mTabShouye.setOnClickListener(this);
		mTabZhouwei.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mTabShouye = (LinearLayout)findViewById(R.id.id_tab_weixin);
		mTabZhouwei = (LinearLayout)findViewById(R.id.id_tab_frd);
		mTabSetting = (LinearLayout)findViewById(R.id.id_tab_setting);
		mImgShouye = (ImageButton)findViewById(R.id.id_tab_weixin_img);
		mImgZhouwei = (ImageButton)findViewById(R.id.id_tab_frd_img);
		mImgSetting = (ImageButton)findViewById(R.id.id_tab_setting_img);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
	public void onClick(View v) {
		resetImg();
		switch (v.getId()) {
		case R.id.id_tab_weixin://�������ҳ��ťʱ���л�ͼƬΪ��ɫ���л�fragmentΪ��ҳ����
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			setSelect(1);
			break;
		case R.id.id_tab_setting:
			setSelect(2);
			break;

		default:
			break;
		}
		
	}
    
    /*
	 * ��ͼƬ����Ϊ��ɫ�ģ��л���ʾ���ݵ�fragment
	 * */
	private void setSelect(int i) {
		android.app.FragmentManager fm = getFragmentManager();
		android.app.FragmentTransaction transaction = fm.beginTransaction();//����һ������
		hideFragment(transaction);//�����Ȱ����е�Fragment�����ˣ�Ȼ�������ٿ�ʼ�������Ҫ��ʾ��Fragment
		switch (i) {
		case 0:
			if (tab01 == null) {
				tab01 = new ShouyeFragment();
				/*
				 * ��Fragment��ӵ���У�public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId��ΪOptional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
				transaction.add(R.id.id_content, tab01);//����ҳ�����Fragment��ӵ�Activity��
			}else {
				transaction.show(tab01);
			}
			mImgShouye.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			if (tab02 == null) {
				tab02 = new ZhouweiFragment();
				transaction.add(R.id.id_content, tab02);
			}else {
				transaction.show(tab02);
			}
			mImgZhouwei.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			if (tab03 == null) {
				tab03 = new SettingFragment();
				transaction.add(R.id.id_content, tab03);
			}else {
				transaction.show(tab03);
			}
			mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
			break;

		default:
			break;
		}
		transaction.commit();//�ύ����
	}


	private void hideFragment(android.app.FragmentTransaction transaction) {
		if (tab01 != null) {
			transaction.hide(tab01);
		}
		if (tab02 != null) {
			transaction.hide(tab02);
		}
		if (tab03 != null) {
			transaction.hide(tab03);
		}

		
	}

	private void resetImg() {
		mImgShouye.setImageResource(R.drawable.tab_weixin_normal);
		mImgZhouwei.setImageResource(R.drawable.tab_find_frd_normal);
		mImgSetting.setImageResource(R.drawable.tab_settings_normal);
	}
}
