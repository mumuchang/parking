package com.parking.fragments;

import com.bmob.demo.sms.LoginActivity;
import com.parking.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;	
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingFragment extends Fragment {

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
