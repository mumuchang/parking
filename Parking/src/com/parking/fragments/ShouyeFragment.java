package com.parking.fragments;



import com.parking.R;
import com.parking.service.PoiService;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;	

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



//定位有关
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.app.Fragment;

//搜索相关
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

public class ShouyeFragment extends Fragment implements SensorEventListener,OnGetPoiSearchResultListener, OnGetSuggestionResultListener {


 // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
	private static final String SENSOR_SERVICE = "sensor";
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    String keystr = "";
    MapView mMapView;
    BaiduMap mBaiduMap;
    int flag =0;//绘制的不是停车场
    
    View view=null;
    //
    
    //路况
    private ImageButton lukuang=null;
    
 //搜索相关
    private PoiSearch mPoiSearch = null;
    private PoiSearch mPoiSearch1 = null;
    
    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest;
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;
    LatLng center = new LatLng(39.92235, 116.380338);
    int radius = 500;
    LatLng southwest = new LatLng( 39.92235, 116.380338 );
    LatLng northeast = new LatLng( 39.947246, 116.414977);
    LatLngBounds searchbound = new LatLngBounds.Builder().include(southwest).include(northeast).build();

    int searchType = 0;  // 搜索的类型，在显示时区分
    
    private String city1="北京";
    private TextView tvSearch;
    
    PoiService overlay=null;

    
    
 // UI相关
    OnCheckedChangeListener radioButtonListener;
    ImageButton requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		//引入我们的布局
		SDKInitializer.initialize(getActivity().getApplicationContext());
		 view=inflater.inflate(R.layout.tab01, container, false);
		requestLocButton = (ImageButton) view.findViewById(R.id.button1);
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mCurrentMode = LocationMode.NORMAL;
        
        
        
        
        OnClickListener btnClickListener = new OnClickListener() {
	         public void onClick(View v) {
	   //重新定位
	        	 mBaiduMap.setMyLocationEnabled(true);
	        	 
	        	 //关闭搜索
	        	
	        	 mBaiduMap.clear();
	        	 mBaiduMap.showMapPoi(false);
	     		
	    		//修改定位数据后刷新图层生效
	    		
	        	 
		            //定位
	          switch (mCurrentMode) {
	            case NORMAL:
	                
	                requestLocButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.loc1));
	                mCurrentMode = LocationMode.FOLLOWING;
	                mBaiduMap
	                        .setMyLocationConfiguration(new MyLocationConfiguration(
	                                mCurrentMode, true, mCurrentMarker));
	                MapStatus.Builder builder = new MapStatus.Builder();
	                builder.overlook(0);
	                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
	                break;
	            case COMPASS:
	                
	                requestLocButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.loc2));
	                mCurrentMode = LocationMode.NORMAL;
	                mBaiduMap
	                        .setMyLocationConfiguration(new MyLocationConfiguration(
	                                mCurrentMode, true, mCurrentMarker));
	                MapStatus.Builder builder1 = new MapStatus.Builder();
	                builder1.overlook(0);
	                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
	                break;
	            case FOLLOWING:
	              
	                requestLocButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.loc3));
	                mCurrentMode = LocationMode.COMPASS;
	                mBaiduMap
	                        .setMyLocationConfiguration(new MyLocationConfiguration(
	                                mCurrentMode, true, mCurrentMarker));
	                break;
	            default:
	                break;
	                }

	            }
	        };
			
	        requestLocButton.setOnClickListener(btnClickListener);
	  
	        RadioGroup group = (RadioGroup) view.findViewById(R.id.radioGroup);
	        radioButtonListener = new OnCheckedChangeListener() {
	            @Override
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	                if (checkedId == R.id.defaulticon) {
	                    // 传入null则，恢复默认图标
	                    mCurrentMarker = null;
	                    mBaiduMap
	                            .setMyLocationConfiguration(new MyLocationConfiguration(
	                                    mCurrentMode, true, null));
	                }
	                if (checkedId == R.id.customicon) {
	                    // 修改为自定义marker
	                    mCurrentMarker = BitmapDescriptorFactory
	                            .fromResource(R.drawable.icon_geo);
	                    mBaiduMap
	                            .setMyLocationConfiguration(new MyLocationConfiguration(
	                                    mCurrentMode, true, mCurrentMarker,
	                                    accuracyCircleFillColor, accuracyCircleStrokeColor));
	                }
	            }
	        };
	        group.setOnCheckedChangeListener(radioButtonListener);

	        // 地图初始化
	        mMapView = (MapView) view.findViewById(R.id.bmapView);
	        mBaiduMap = mMapView.getMap();
	        // 开启定位图层
	        mBaiduMap.setMyLocationEnabled(true);
	        // 定位初始化dubudi
	        mLocClient = new LocationClient( getActivity().getApplicationContext());
	        mLocClient.registerLocationListener(myListener);
	        LocationClientOption option = new LocationClientOption();
	        option.setOpenGps(true); // 打开gps
	        option.setCoorType("bd09ll"); // 设置坐标类型
	        option.setScanSpan(1000);
	        mLocClient.setLocOption(option);
	        mLocClient.start();
	        mLocClient.requestLocation();
	        
	        //显示路况
	        lukuang=(ImageButton)view.findViewById(R.id.lukuang);
	        
	        lukuang.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mBaiduMap.isTrafficEnabled()==false){
						lukuang.setBackgroundDrawable(getResources().getDrawable(R.drawable.lukuangstart));
						mBaiduMap.setTrafficEnabled(true);
					}else{
						lukuang.setBackgroundDrawable(getResources().getDrawable(R.drawable.lukuangclose));
						mBaiduMap.setTrafficEnabled(false);
					}
				}
	        	
	        });
	        
	        
	       
	        
        
        
        
        //搜索
     // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        
        mPoiSearch1 = PoiSearch.newInstance();
        mPoiSearch1.setOnGetPoiSearchResultListener(this);
        
     // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        
        keyWorldsView = (AutoCompleteTextView) view.findViewById(R.id.searchkey);
        tvSearch = (TextView) view.findViewById(R.id.tvSearch);
        
        tvSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchButtonProcess(v);
				// 关闭定位图层
		        mBaiduMap.setMyLocationEnabled(false);
			}
        	
        });
     
   
        
        sugAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line,suggest);
        
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);
        
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence cs, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (cs.length() <= 0) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())//ruhe获得定位城市
                                .keyword(cs.toString()).city(city1.toString()));
            }



			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
        	
        });
		
		return view;
	}



	@Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

	



	
	/**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
            	Log.e("aaaaa", "aaaaaaaaa");
            	return;
                
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            
            //requestLocButton.setText("loc");
            
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    //地图生命周期
    @Override
	public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
	public void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
	public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
	public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mPoiSearch.destroy();
        mPoiSearch1.destroy(); 
	    mSuggestionSearch.destroy();
        super.onDestroy();
    }



    //搜索处理
	
	 public void searchButtonProcess(View v) {
	        searchType = 1;
	       
	       keystr=keyWorldsView.getText().toString();
	        mBaiduMap.clear();
	        flag=0;
	        mPoiSearch.searchInCity((new PoiCitySearchOption())
	                .city(city1).keyword(keystr).pageNum(loadIndex));
	      //  flag=1;
	        //mPoiSearch1.searchInCity((new PoiCitySearchOption())
	             //   .city(city).keyword(keystr+"停车场").pageNum(loadIndex));
	    }
	 

	    public void goToNextPage(View v) {
	        loadIndex++;
	        searchButtonProcess(null);
	    }
	 
	    @SuppressWarnings("unused")
		private class MySearch extends PoiCitySearchOption {
	    	
	    }
			
	    /**
	     * 响应周边搜索按钮点击事件
	     *
	     * @param v
	     */
	    public void  searchNearbyProcess(View v) {
	        searchType = 2;
	        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyWorldsView.getText()
	                .toString()).sortType(PoiSortType.distance_from_near_to_far).location(center)
	                .radius(radius).pageNum(loadIndex);
	        mPoiSearch.searchNearby(nearbySearchOption);
	    }

	    /**
	     * 响应区域搜索按钮点击事件
	     *
	     * @param v
	     */
	    public void searchBoundProcess(View v) {
	        searchType = 3;

	        mPoiSearch.searchInBound(new PoiBoundSearchOption().bound(searchbound)
	                .keyword(keyWorldsView.getText().toString()));

	    }
	    
	    
	    /**
	     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
	     * @param res
	     */
		@Override
		public void onGetSuggestionResult(SuggestionResult res) {
			// TODO Auto-generated method stub
			if (res == null || res.getAllSuggestions() == null) {
	            return;
	        }    
	        suggest = new ArrayList<String>();
	        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
	            if (info.key != null) {
	                suggest.add(info.key);
	            }
	        }
	        sugAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, suggest);
	        
	        keyWorldsView.setAdapter(sugAdapter);
	        sugAdapter.notifyDataSetChanged();
		}


		/**
	     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
	     * @param result
	     */
		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			// TODO Auto-generated method stub
			
			 if (result.error != SearchResult.ERRORNO.NO_ERROR) {
		            Toast.makeText(getActivity().getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
		                    .show();
		        } else {
		            Toast.makeText(getActivity().getApplicationContext(), result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
		                    .show();
		        }
		}


		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
			// TODO Auto-generated method stub
			
		}


		/**
	     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
	     * @param result
	     */
		@Override
		public void onGetPoiResult(PoiResult result) {
			// TODO Auto-generated method stub
			if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
	            Toast.makeText(getActivity().getApplicationContext(), "未找到结果", Toast.LENGTH_LONG)
	                    .show();
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	       
	        		//目的地
	           overlay = new MyPoiOverlay(mBaiduMap,flag);
		            mBaiduMap.setOnMarkerClickListener(overlay);
		            overlay.setData(result);
		            overlay.addToMap();
		            overlay.zoomToSpan();
		        switch( searchType ) {
	                case 2:
	                    showNearbyArea(center, radius);
	                    break;
	                default:
	                    break;
	            }
	            flag=1;
	            mPoiSearch1.searchInCity((new PoiCitySearchOption())
	                    .city(city1).keyword(keystr+"停车场").pageNum(loadIndex));
	            
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

	            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
	            String strInfo = "在";
	            for (CityInfo cityInfo : result.getSuggestCityList()) {
	                strInfo += cityInfo.city;
	                strInfo += ",";
	            }
	            strInfo += "找到结果";
	            Toast.makeText(getActivity().getApplicationContext(), strInfo, Toast.LENGTH_LONG)
	                    .show();
	        }
		}


		private class MyPoiOverlay extends PoiService {

	        public MyPoiOverlay(BaiduMap baiduMap,int f) {
	            super(baiduMap,f);
	        }

	        @Override
	        public boolean onPoiClick(int index) {
	            super.onPoiClick(index);
	            PoiInfo poi = getPoiResult().getAllPoi().get(index);
	            // if (poi.hasCaterDetails) {
	            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
	                    .poiUid(poi.uid));
	            mPoiSearch1.searchPoiDetail((new PoiDetailSearchOption())
	                    .poiUid(poi.uid));
	            // }
	            return true;
	        }
	    }

		/**
	     * 对周边检索的范围进行绘制
	     * @param center
	     * @param radius
	     */
	    public void showNearbyArea( LatLng center, int radius) {
	        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
	                .fromResource(R.drawable.icon_geo);
	        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
	        mBaiduMap.addOverlay(ooMarker);

	        OverlayOptions ooCircle = new CircleOptions().fillColor( 0xCCCCCC00 )
	                .center(center).stroke(new Stroke(5, 0xFFFF00FF ))
	                .radius(radius);
	        mBaiduMap.addOverlay(ooCircle);
	    }

    
    
    
    
}
