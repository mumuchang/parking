package com.parking.fragments;


import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import com.bmob.demo.sms.bean.ParkinglotInfo;
import com.parking.R;
import com.parking.service.DetailPopupWindow;
import com.parking.service.PoiService;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//��λ�й�
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

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

import android.app.Fragment;

//�������
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

public class ShouyeFragment extends Fragment implements SensorEventListener, OnGetPoiSearchResultListener, OnGetSuggestionResultListener {


    // ��λ���
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
    int flag = 0;//���ƵĲ���ͣ����

    View view = null;
    //

    //·��
    private ImageButton lukuang = null;

    //�������
    private PoiSearch mPoiSearch = null;
    private PoiSearch mPoiSearch1 = null;

    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest;
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;
    LatLng center = new LatLng(39.92235, 116.380338);
    int radius = 500;
    LatLng southwest = new LatLng(39.92235, 116.380338);
    LatLng northeast = new LatLng(39.947246, 116.414977);
    LatLngBounds searchbound = new LatLngBounds.Builder().include(southwest).include(northeast).build();

    int searchType = 0;  // ���������ͣ�����ʾʱ����

    private String city1 = "����";
    private TextView tvSearch;

    PoiService overlay = null;


    // UI���
    OnCheckedChangeListener radioButtonListener;
    ImageButton requestLocButton;
    boolean isFirstLoc = true; // �Ƿ��״ζ�λ
    private MyLocationData locData;
    private float direction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //�������ǵĲ���
        //�������ǵĲ���
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view = inflater.inflate(R.layout.tab01, container, false);
        requestLocButton = (ImageButton) view.findViewById(R.id.button1);
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//��ȡ�������������
        mCurrentMode = LocationMode.NORMAL;


        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                //���¶�λ
                mBaiduMap.setMyLocationEnabled(true);

                //�ر�����

                mBaiduMap.clear();
                mBaiduMap.showMapPoi(false);

                //�޸Ķ�λ���ݺ�ˢ��ͼ����Ч


                //��λ
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
                    // ����null�򣬻ָ�Ĭ��ͼ��
                    mCurrentMarker = null;
                    mBaiduMap
                            .setMyLocationConfiguration(new MyLocationConfiguration(
                                    mCurrentMode, true, null));
                }
                if (checkedId == R.id.customicon) {
                    // �޸�Ϊ�Զ���marker
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

        // ��ͼ��ʼ��
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // ������λͼ��
        mBaiduMap.setMyLocationEnabled(true);
        // ��λ��ʼ��dubudi
        mLocClient = new LocationClient(getActivity().getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ��gps
        option.setCoorType("bd09ll"); // ������������
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();

        //��ʾ·��
        lukuang = (ImageButton) view.findViewById(R.id.lukuang);

        lukuang.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mBaiduMap.isTrafficEnabled() == false) {
                    lukuang.setBackgroundDrawable(getResources().getDrawable(R.drawable.lukuangstart));
                    mBaiduMap.setTrafficEnabled(true);
                } else {
                    lukuang.setBackgroundDrawable(getResources().getDrawable(R.drawable.lukuangclose));
                    mBaiduMap.setTrafficEnabled(false);
                }
            }

        });


        //����
        // ��ʼ������ģ�飬ע�������¼�����
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mPoiSearch1 = PoiSearch.newInstance();
        mPoiSearch1.setOnGetPoiSearchResultListener(this);

        // ��ʼ����������ģ�飬ע�Ὠ�������¼�����
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        keyWorldsView = (AutoCompleteTextView) view.findViewById(R.id.searchkey);
        tvSearch = (TextView) view.findViewById(R.id.tvSearch);

        tvSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mBaiduMap.setMyLocationEnabled(false);
                // TODO Auto-generated method stub
                searchButtonProcess(v);
                // �رն�λͼ��

            }

        });


        sugAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, suggest);

        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);

        /**
         * ������ؼ��ֱ仯ʱ����̬���½����б�
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

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
                 * ʹ�ý������������ȡ�����б������onSuggestionResult()�и���
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())//ruhe��ö�λ����
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
                    // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
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
     * ��λSDK��������
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view ���ٺ��ڴ����½��յ�λ��
            if (location == null || mMapView == null) {
                Log.e("aaaaa", "aaaaaaaaa");
                return;

            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
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

    //��ͼ��������
    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //Ϊϵͳ�ķ��򴫸���ע�������
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onStop() {
        //ȡ��ע�ᴫ��������
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // �˳�ʱ���ٶ�λ
        mLocClient.stop();
        // �رն�λͼ��
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mPoiSearch.destroy();
        mPoiSearch1.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }


    //��������

    public void searchButtonProcess(View v) {
        searchType = 1;
        mBaiduMap.clear();
        keystr = keyWorldsView.getText().toString();
        //mBaiduMap.clear();
        flag = 0;
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city1).keyword(keystr).pageNum(loadIndex));
        //flag=1;
        //mPoiSearch1.searchInCity((new PoiCitySearchOption())
        //   .city(city).keyword(keystr+"ͣ����").pageNum(loadIndex));
    }


    public void goToNextPage(View v) {
        loadIndex++;
        searchButtonProcess(null);
    }

    @SuppressWarnings("unused")
    private class MySearch extends PoiCitySearchOption {

    }

    /**
     * ��Ӧ�ܱ�������ť����¼�
     *
     * @param v
     */
    public void searchNearbyProcess(View v) {
        searchType = 2;
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyWorldsView.getText()
                .toString()).sortType(PoiSortType.distance_from_near_to_far).location(center)
                .radius(radius).pageNum(loadIndex);
        mPoiSearch.searchNearby(nearbySearchOption);
    }

    /**
     * ��Ӧ����������ť����¼�
     *
     * @param v
     */
    public void searchBoundProcess(View v) {
        searchType = 3;

        mPoiSearch.searchInBound(new PoiBoundSearchOption().bound(searchbound)
                .keyword(keyWorldsView.getText().toString()));

    }


    /**
     * ��ȡ���߽�������������õ�requestSuggestion���ص��������
     *
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
     * ��ȡPOI��������������õ�searchPoiDetail���ص��������
     *
     * @param result
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {
        // TODO Auto-generated method stub

        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getActivity().getApplicationContext(), "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), result.getName() + " : " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
        // TODO Auto-generated method stub

    }


    /**
     * ��ȡPOI�������������searchInCity��searchNearby��searchInBound���ص��������
     *
     * @param result
     */
    @Override
    public void onGetPoiResult(PoiResult result) {
        // TODO Auto-generated method stub
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(getActivity().getApplicationContext(), "δ�ҵ����", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {

            //Ŀ�ĵ�
            overlay = new MyPoiOverlay(mBaiduMap, flag);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            switch (searchType) {
                case 2:
                    showNearbyArea(center, radius);
                    break;
                default:
                    break;
            }
            flag = 1;
            String parkinglot = keystr + "停车场";
            mPoiSearch1.searchInCity((new PoiCitySearchOption())
                    .city(city1).keyword(parkinglot).pageNum(loadIndex));

            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // ������ؼ����ڱ���û���ҵ����������������ҵ�ʱ�����ذ����ùؼ�����Ϣ�ĳ����б�
            String strInfo = "��";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "�ҵ����";
            Toast.makeText(getActivity().getApplicationContext(), strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }


    private class MyPoiOverlay extends PoiService {

        public MyPoiOverlay(BaiduMap baiduMap, int f) {
            super(baiduMap, f);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            String name = poi.name;
            String address = poi.address;
            BmobQuery<ParkinglotInfo> q1 = new BmobQuery<ParkinglotInfo>();
            q1.addWhereEqualTo("parkinglot_name", name);
            BmobQuery<ParkinglotInfo> q2 = new BmobQuery<ParkinglotInfo>();
            q2.addWhereEqualTo("description", address);
            List<BmobQuery<ParkinglotInfo>> andQuery = new ArrayList<BmobQuery<ParkinglotInfo>>();
            andQuery.add(q1);
            andQuery.add(q2);
            final BmobQuery<ParkinglotInfo> query = new BmobQuery<ParkinglotInfo>();
            query.and(andQuery);
            final List<ParkinglotInfo> queryResult = new ArrayList<ParkinglotInfo>();
            query.findObjects(new FindListener<ParkinglotInfo>() {
                @Override
                public void done(List<ParkinglotInfo> list, BmobException e) {
                    if (e == null){
                        if (list.size() != 0 ){
                            DetailPopupWindow detailPopupWindow = new DetailPopupWindow(getActivity());
                            detailPopupWindow.showAtLocation(getActivity().findViewById(R.id.tab01),Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0 , 0);
                            TextView popName = (TextView) detailPopupWindow.view.findViewById(R.id.popName);
                            popName.setText(list.get(0).getParkinglot_name());
                            TextView popAddress = (TextView) detailPopupWindow.view.findViewById(R.id.address);
                            popAddress.setText("地址："+ list.get(0).getDescription());
                            TextView popLeftNum = (TextView) detailPopupWindow.view.findViewById(R.id.leftNum);
                            popLeftNum.setText("空余车位：" + list.get(0).getCurrentLeftNum());
                        }
                    }else {
                        Log.e("WRONG!!!!!!", e.getMessage());
                    }
                }
            });

            mPoiSearch1.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            return true;
        }
    }

    /**
     * ���ܱ߼����ķ�Χ���л���
     *
     * @param center
     * @param radius
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_geo);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        OverlayOptions ooCircle = new CircleOptions().fillColor(0xCCCCCC00)
                .center(center).stroke(new Stroke(5, 0xFFFF00FF))
                .radius(radius);
        mBaiduMap.addOverlay(ooCircle);
    }


}
