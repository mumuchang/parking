package com.parking.fragments;

import cn.bmob.v3.BmobUser;

import com.bmob.demo.sms.LoginActivity;
import com.bmob.demo.sms.bean.User;
import com.parking.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;	
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SettingFragment extends Fragment {

	TextView name;
	Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.tab03, container, false);
		
	
	}

    @Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);
        name=(TextView) getActivity().findViewById(R.id.name);
        User user = BmobUser.getCurrentUser(User.class);
        if(user!=null){
        	name.setText(user.getUsername());
        }
    	btn=(Button) getActivity().findViewById(R.id.denglu);
        btn.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	Intent intent = new Intent(getActivity()
                        .getApplicationContext(), LoginActivity.class);
			  startActivity(intent);		 
            }  
        });  
    }  
    
	
}
