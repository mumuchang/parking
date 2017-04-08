package com.parking.fragments;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.parking.R;
import com.parking.service.LocationApplication;
import com.parking.service.LocationService;

import android.app.Fragment;
import android.os.Bundle;	
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


public class ShouyeFragment extends Fragment  {

	private BaiduMap mBaiduMap;
	private MapView mMapView = null;
	private Overlay needremove=null;
	private LatLng cenpt=new LatLng(26.89,112.61);;
	private LocationService locationService;
	private Button locbutton;
	
	long startTime;
    long costTime;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		SDKInitializer.initialize(getActivity().getApplicationContext());
		View view=inflater.inflate(R.layout.tab01, container, false);
		mMapView = (MapView)view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		locbutton=(Button)view.findViewById(R.id.request);
		locationService = ((LocationApplication) getActivity().getApplication()).locationService; 
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		
		locationService.start();// 定位SDK
		locationService.stop();
		return view;
	}
	 
	
	/***
	 * Stop location service
	 */
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		locationService.unregisterListener(mListener); //注销掉监听
		locationService.stop(); //停止定位服务
		super.onStop();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// -----------location config ------------
		locationService = ((LocationApplication) getActivity().getApplication()).locationService; 
		//获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(mListener);
		//注册监听
		int type = getActivity().getIntent().getIntExtra("from", 0);
		
		if (type == 0) {
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
		} else if (type == 1) {
			locationService.setLocationOption(locationService.getOption());
		}
		locbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
					locationService.start();// 定位SDK
			
					locationService.stop();
				
			}
		});
	}
	 @Override
	public void onDestroy(){
		super.onDestroy();
		mMapView.onDestroy();
	}
	 @Override
	public void onResume(){
		super.onResume();
		mMapView.onResume();
	}
	 @Override
	public void onPause(){
		super.onPause();
		mMapView.onPause();
	}
	 
	 /*****
		 * @see copy funtion to you project
		 * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
		 *
		 */
		private BDLocationListener mListener = new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (null != location && location.getLocType() != BDLocation.TypeServerError) {
					//标记当前位置
					cenpt=new LatLng(location.getLatitude(),location.getLongitude());
					 
				}
				if(needremove!=null)
					needremove.remove();
			    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.loc);
			    OverlayOptions option = new MarkerOptions().position(cenpt).icon(bitmap);//构建一个MarkerOption对象
			    needremove=mBaiduMap.addOverlay(option);
	      
			    MapStatus mMapStatus=new MapStatus.Builder().target(cenpt).zoom(18).build();
			    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
			        //改变地图状态
		        mBaiduMap.setMapStatus(mMapStatusUpdate); 
		        
				
			}

			public void onConnectHotSpotMessage(String s, int i){
	        }
		};
}
