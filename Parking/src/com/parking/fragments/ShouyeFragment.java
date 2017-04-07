package com.parking.fragments;



import com.parking.R;

import android.app.Fragment;
import android.os.Bundle;	

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShouyeFragment extends Fragment  {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.tab01, container, false);
	}
}
