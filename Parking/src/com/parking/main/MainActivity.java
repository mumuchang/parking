package com.parking.main;

import cn.bmob.v3.Bmob;





import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.bmob.demo.sms.LoginActivity;
import com.parking.R;
import com.parking.fragments.SettingFragment;
import com.parking.fragments.ShouyeFragment;
import com.parking.fragments.ZhouweiFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements OnClickListener{

	//底部的3个导航控件
		private LinearLayout mTabShouye;
		private LinearLayout mTabZhouwei;
		private LinearLayout mTabSetting;
		//底部3个导航控件中的图片按钮
		private ImageButton mImgShouye;
		private ImageButton mImgZhouwei;
		private ImageButton mImgSetting;
		//初始化4个Fragment
		private Fragment tab01;
		private android.app.Fragment tab02;
		private android.app.Fragment tab03;
		private Button btn;
		
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(MainActivity.this, "19872817e4d9a4070addbbb47c9564f5"); 
        initView();//初始化所有的view
		initEvents();
		setSelect(0);//默认显示首页界面
	   
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
    
  
    /*
	 * 将图片设置为亮色的；切换显示内容的fragment
	 * */
	private void setSelect(int i) {
		android.app.FragmentManager fm = getFragmentManager();
		android.app.FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
		hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
		switch (i) {
		case 0:
			if (tab01 == null) {
				tab01 = new ShouyeFragment();
				
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
				transaction.add(R.id.id_content, tab01);//将首页界面的Fragment添加到Activity中
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
		transaction.commit();//提交事务
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



	  @Override
		public void onClick(View v) {
			resetImg();
			switch (v.getId()) {
			case R.id.id_tab_weixin://当点击首页按钮时，切换图片为亮色，切换fragment为首页界面
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
	    
}
